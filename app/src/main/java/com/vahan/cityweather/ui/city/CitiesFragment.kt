package com.vahan.cityweather.ui.city

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vahan.cityweather.R
import com.vahan.cityweather.databinding.FragmentCitiesBinding
import com.vahan.cityweather.util.LocationHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitiesFragment : Fragment() {
    private val viewModel: CitiesViewModel by viewModel()
    private lateinit var binding: FragmentCitiesBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //We need this variable to get location when user turned on location in settings
    private var isLocationSettingEnabled = false
    private var isLocationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLocationPermissionGranted = LocationHelper.checkPermissions(requireContext())
        if (isLocationPermissionGranted) {
            getLocation()
        } else {
            requestPermissionAndGetLocation()
        }

        val adapter = CitiesAdapter(requireContext(), mutableListOf()) {
            findNavController().navigate(CitiesFragmentDirections.actionCitiesFragmentToWeatherFragment(it))
        }
        binding.rvCities.adapter = adapter

        viewModel.citiesLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(requireContext(), R.string.empty_data, Toast.LENGTH_LONG).show()
            } else {
                adapter.setItems(it)
            }
            showLoading(false)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showLoading(false)
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun requestPermissionAndGetLocation() {
        //Register callback for location permissions
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.containsKey(ACCESS_FINE_LOCATION) && permissions.get(ACCESS_FINE_LOCATION) == true -> {
                    getLocation()
                }
                permissions.containsKey(ACCESS_COARSE_LOCATION) && permissions.get(ACCESS_COARSE_LOCATION) == true -> {
                    getLocation()
                }
                else -> {
                    // No location access granted.
                    Toast.makeText(requireContext(), R.string.location_permission_error, Toast.LENGTH_LONG).show()
                }
            }

        }
        //Request permissions
        locationPermissionRequest.launch(
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    // We check permissions before calling this fun but lint can't understand it, so we need this annotation
    private fun getLocation() {
        isLocationSettingEnabled = LocationHelper.isLocationEnabled(requireContext())
        if (isLocationSettingEnabled) {
            //If location enabled in settings we get last location and get nearest cities for that location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        showLoading(true)
                        viewModel.getCities(it.latitude, it.longitude)
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), R.string.get_location_error, Toast.LENGTH_LONG).show()
                }
        } else {
            //If location id disabled from settings navigate to system location setting
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        //Check the case when user return after changing location setting
        if (isLocationPermissionGranted && !isLocationSettingEnabled && LocationHelper.isLocationEnabled(requireContext()))
            getLocation()
    }

    private fun showLoading(show: Boolean) {
        binding.layoutLoading.root.visibility = if (show) View.VISIBLE else View.GONE
        binding.layoutContent.visibility = if (show) View.GONE else View.VISIBLE
    }

}