package com.vahan.cityweather.app

import com.vahan.cityweather.city.City
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.vahan.cityweather.weather.remote.CurrentWeatherRemoteModel
import com.vahan.cityweather.weather.remote.daily.DailyWeatherListRemoteModel

interface CityWeatherRemoteApi {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<CurrentWeatherRemoteModel>

    @GET("data/2.5/forecast")
    suspend fun getDailyWeatherList(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<DailyWeatherListRemoteModel>

    @GET("geo/1.0/reverse")
    suspend fun getNearestLocations(@Query("lat") lat: Double, @Query("lon") lon: Double): Response<List<City>>

}