package com.vahan.cityweather.weather.local

import com.vahan.cityweather.app.Result
import com.vahan.cityweather.weather.CurrentWeather
import com.vahan.cityweather.weather.DailyWeather
import com.vahan.cityweather.weather.WeatherMapper

interface WeatherLocalDataSource {
    suspend fun getDailyWeatherList(lat: Double, lon: Double): Result<List<DailyWeather>?>
    suspend fun cacheDailyWeather(lat: Double, lon: Double, dailyWeatherList: List<DailyWeatherEntity>)
    suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?>
    suspend fun cacheCurrentWeather(lat: Double, lon: Double, currentWeather: CurrentWeatherEntity)
}

class WeatherLocalDataSourceImpl(
    private val currentWeatherLocalDataSource: CurrentWeatherDao,
    private val dailyWeatherLocalDataSource: DailyWeatherDao
) : WeatherLocalDataSource {


    //I don't do error handling when getting form db
    override suspend fun getDailyWeatherList(lat: Double, lon: Double): Result<List<DailyWeather>?> {
        val localDailyWeather = dailyWeatherLocalDataSource.getDailyWeatherList(lat, lon)
        val result = localDailyWeather?.map { WeatherMapper.dailyWeatherFromLocalModel(it) }
        return Result.Success(result)
    }

    override suspend fun cacheDailyWeather(lat: Double, lon: Double, dailyWeatherList: List<DailyWeatherEntity>) {
        dailyWeatherLocalDataSource.delete(lat, lon)
        dailyWeatherLocalDataSource.insertAll(*dailyWeatherList.toTypedArray())
    }


    //I don't do error handling when getting form db
    override suspend fun getCurrentWeather(lat: Double, lon: Double): Result<CurrentWeather?> {
        val localDailyWeather = currentWeatherLocalDataSource.getCurrentWeather(lat, lon)
        val result = localDailyWeather?.let { WeatherMapper.currentWeatherFromLocalModel(it) }
        return Result.Success(result)
    }

    override suspend fun cacheCurrentWeather(lat: Double, lon: Double, currentWeather: CurrentWeatherEntity) {
        currentWeatherLocalDataSource.delete(lat, lon)
        currentWeatherLocalDataSource.insert(currentWeather)
    }
}