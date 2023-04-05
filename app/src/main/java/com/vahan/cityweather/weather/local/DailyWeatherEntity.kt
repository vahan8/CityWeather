package com.vahan.cityweather.weather.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "dailyWeather")
data class DailyWeatherEntity(
    val lat: Double,
    val lon: Double,
    val date: Long,
    val minTemperature: Double,
    val maxTemperature: Double,
    @PrimaryKey(autoGenerate = true) val _id: Long = 0L
)