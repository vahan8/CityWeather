package com.vahan.cityweather.city

import com.vahan.cityweather.app.CityWeatherRemoteApi
import com.vahan.cityweather.app.Result

interface CityRepository {
    suspend fun getCities(lat: Double, lon: Double): Result<List<City>?>
}

class CityRepositoryImpl(private val dataSource: CityWeatherRemoteApi) : CityRepository {

    //Retrofit guarantee that suspend function call will be done on Dispatchers.IO
    //Returns nearest cities for the given location
    override suspend fun getCities(lat: Double, lon: Double): Result<List<City>?> {
        val response = dataSource.getNearestLocations(lat, lon)
        return if (response.isSuccessful) {
            Result.Success(response.body())
        } else {
            Result.Error(response.message())
        }
    }
}