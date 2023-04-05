package com.vahan.cityweather.weather.remote

import com.vahan.cityweather.app.Result
import com.vahan.cityweather.app.CityWeatherRemoteApi
import com.vahan.cityweather.weather.CurrentWeather
import com.vahan.cityweather.weather.DailyWeather
import com.vahan.cityweather.weather.WeatherMapper

interface WeatherRemoteDataSource {
    suspend fun getDailyWeatherList(lat: Double, lon: Double): Result<List<DailyWeather>?>
    suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?>
}

class WeatherRemoteDataSourceImpl(
    private val api: CityWeatherRemoteApi
) : WeatherRemoteDataSource {

    override suspend fun getDailyWeatherList(lat: Double, lon: Double): Result<List<DailyWeather>?> {
        val response = api.getDailyWeatherList(lat, lon)
        if (response.isSuccessful) {
            val result = response.body()?.list?.map { WeatherMapper.dailyWeatherFromRemoteModel(it) }
            return Result.Success(result)
        } else {
            return Result.Error(response.message())
        }
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?> {
        val response = api.getCurrentWeather(lat, lon)
        if (response.isSuccessful) {
            val result = WeatherMapper.currentWeatherFromRemoteModel(response.body() as CurrentWeatherRemoteModel)
            return Result.Success(result)
        } else {
            return Result.Error(response.message())
        }
    }

}