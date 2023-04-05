package com.vahan.cityweather.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vahan.cityweather.weather.local.CurrentWeatherDao
import com.vahan.cityweather.weather.local.CurrentWeatherEntity
import com.vahan.cityweather.weather.local.DailyWeatherDao
import com.vahan.cityweather.weather.local.DailyWeatherEntity


@Database(entities = [CurrentWeatherEntity::class, DailyWeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun dailyWeatherDao(): DailyWeatherDao
}