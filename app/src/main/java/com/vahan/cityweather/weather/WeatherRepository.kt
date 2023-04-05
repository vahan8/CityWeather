package com.vahan.cityweather.weather.remote

import com.vahan.cityweather.app.Result
import com.vahan.cityweather.util.ConnectivityHelper
import com.vahan.cityweather.weather.CurrentWeather
import com.vahan.cityweather.weather.DailyWeather
import com.vahan.cityweather.weather.local.CurrentWeatherEntity
import com.vahan.cityweather.weather.local.DailyWeatherEntity
import com.vahan.cityweather.weather.local.WeatherLocalDataSource

interface WeatherRepository {
    suspend fun getDailyWeatherList(lat: Double, lon: Double): Result<List<DailyWeather>?>
    suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?>
}

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    //Retrofit and Room guarantee that suspend function call will be done on Dispatchers.IO
    override suspend fun getDailyWeatherList(lat: Double, lon: Double): Result<List<DailyWeather>?> {
        return if (ConnectivityHelper.hasInternetConnection()) {
            val remoteResult = remoteDataSource.getDailyWeatherList(lat, lon)
            //If response is successful cache data
            if (remoteResult is Result.Success && remoteResult.data != null) {
                cacheDailyWeather(lat, lon, remoteResult.data)
            }
            return remoteResult
        } else {
            localDataSource.getDailyWeatherList(lat, lon)
        }
    }


    private suspend fun cacheDailyWeather(lat: Double, lon: Double, dailyWeatherList: List<DailyWeather>) {
        val dailyWeatherEntities = dailyWeatherList.map {
            DailyWeatherEntity(lat, lon, it.date, it.minTemperature, it.maxTemperature)
        }
        localDataSource.cacheDailyWeather(lat, lon, dailyWeatherEntities)
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?> {
        return if (ConnectivityHelper.hasInternetConnection()) {
            val remoteResult = remoteDataSource.getCurrentWeather(lat, lon)
            //If response is successful cache data
            if (remoteResult is Result.Success && remoteResult.data != null) {
                cacheCurrentWeather(lat, lon, remoteResult.data)
            }
            return remoteResult
        } else {
            localDataSource.getCurrentWeather(lat, lon)
        }
    }

    private suspend fun cacheCurrentWeather(lat: Double, lon: Double, currentWeather: CurrentWeather) {
        val currentWeatherEntity = CurrentWeatherEntity(lat, lon, currentWeather.weatherDescription, currentWeather.temperature)
        localDataSource.cacheCurrentWeather(lat, lon, currentWeatherEntity)
    }
}