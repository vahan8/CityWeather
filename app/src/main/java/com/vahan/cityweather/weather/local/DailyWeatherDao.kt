package com.vahan.cityweather.weather.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DailyWeatherDao {

    @Insert
    suspend fun insertAll(vararg dailyWeather: DailyWeatherEntity)

    // we store in db only single row which is the last weather we got, because there is useless to store older data
    @Query("SELECT * FROM dailyWeather WHERE lat = :lat AND lon = :lon")
    suspend fun getDailyWeatherList(lat: Double, lon: Double): List<DailyWeatherEntity>?

    @Query("DELETE FROM dailyWeather WHERE lat = :lat AND lon = :lon")
    suspend fun delete(lat: Double, lon: Double)
}
