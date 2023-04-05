package com.vahan.cityweather.weather.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrentWeatherDao {

    @Insert
    suspend fun insert(currentWeather: CurrentWeatherEntity)

    // we store in db only single row for the city which is the last weather we got, because there is useless to store older data
    @Query("SELECT * FROM currentWeather WHERE lat = :lat AND lon = :lon LIMIT 1")
    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherEntity?

    @Query("DELETE FROM currentWeather WHERE lat = :lat AND lon = :lon")
    suspend fun delete(lat: Double, lon: Double)
}

