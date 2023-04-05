package com.vahan.cityweather.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahan.cityweather.app.Result
import com.vahan.cityweather.city.City
import com.vahan.cityweather.city.CityRepository
import kotlinx.coroutines.launch

class CitiesViewModel(
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _citiesLiveData: MutableLiveData<List<City>?> = MutableLiveData()
    val citiesLiveData: LiveData<List<City>?>
        get() = _citiesLiveData

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    // Api can return 1 or several cities for location, but in most of the cases it returns 1 city
    fun getCities(lat: Double, lon: Double) {
        // Start a coroutine
        viewModelScope.launch {
            val result = cityRepository.getCities(lat, lon)
            when (result) {
                is Result.Success -> _citiesLiveData.value = result.data
                is Result.Error -> _errorLiveData.value = result.error ?: "Something went wrong"
            }

        }

    }

}