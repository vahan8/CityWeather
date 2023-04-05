package com.vahan.cityweather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vahan.cityweather.R
import com.vahan.cityweather.databinding.FragmentWeatherBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: FragmentWeatherBinding
    private val args: WeatherFragmentArgs by navArgs<WeatherFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData(args.city.lat, args.city.lon)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCityName.text = args.city.name

        val adapter = DailyWeatherAdapter(requireContext(), mutableListOf())
        binding.rvDailyWeather.adapter = adapter

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(requireContext(), getString(R.string.empty_data), Toast.LENGTH_LONG).show()
            } else {
                binding.tvWeatherDescription.text = it.weatherDescription
                binding.tvTemperature.text = getString(R.string.temperature_celsius, it.temperature)
            }
        }
        viewModel.dailyWeatherLiveData.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        viewModel.finishLoadingLiveData.observe(viewLifecycleOwner) {
            binding.layoutContent.visibility = View.VISIBLE
            binding.layoutLoading.root.visibility = View.GONE
        }
    }


}