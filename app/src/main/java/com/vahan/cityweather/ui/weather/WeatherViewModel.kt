package com.vahan.cityweather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vahan.cityweather.app.Result
import com.vahan.cityweather.weather.CurrentWeather
import com.vahan.cityweather.weather.DailyWeather
import com.vahan.cityweather.weather.remote.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _currentWeatherLiveData: MutableLiveData<CurrentWeather?> = MutableLiveData()
    val currentWeatherLiveData: LiveData<CurrentWeather?>
        get() = _currentWeatherLiveData

    private val _dailyWeatherLiveData: MutableLiveData<List<DailyWeather>> = MutableLiveData()
    val dailyWeatherLiveData: LiveData<List<DailyWeather>>
        get() = _dailyWeatherLiveData

    private val _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    private val _finishLoadingLiveData: MutableLiveData<Unit> = MutableLiveData()
    val finishLoadingLiveData: LiveData<Unit>
        get() = _finishLoadingLiveData

    fun getData(lat: Double, lon: Double) {
        // Start a coroutine
        viewModelScope.launch {
            val dailyWeathers = weatherRepository.getDailyWeatherList(lat, lon)
            when (dailyWeathers) {
                is Result.Success -> _dailyWeatherLiveData.value = dailyWeathers.data ?: listOf()
                is Result.Error -> _errorLiveData.value = dailyWeathers.error ?: "Something went wrong"
            }
            val currentWeather = weatherRepository.getCurrentWeather(lat, lon)
            when (currentWeather) {
                is Result.Success -> _currentWeatherLiveData.value = currentWeather.data as CurrentWeather?
                is Result.Error -> _errorLiveData.value = currentWeather.error ?: "Something went wrong"
            }
            _finishLoadingLiveData.value = Unit
        }
    }
}