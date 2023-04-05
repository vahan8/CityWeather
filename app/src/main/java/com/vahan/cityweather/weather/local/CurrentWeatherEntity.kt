package com.vahan.cityweather.weather.local

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "currentWeather")
data class CurrentWeatherEntity(
    val lat: Double,
    val lon: Double,
    val weatherDescription: String,
    val temperature: Double,
    @PrimaryKey(autoGenerate = true) val _id: Long = 0L
)