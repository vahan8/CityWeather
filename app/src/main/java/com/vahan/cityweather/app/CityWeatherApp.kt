package com.vahan.cityweather.app

import android.app.Application
import com.vahan.cityweather.di.databaseModule
import com.vahan.cityweather.di.networkModule
import com.vahan.cityweather.di.repositoryModule
import com.vahan.cityweather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CityWeatherApp: Application() {

    companion object {
        const val DB_NAME = "city_weather"
        val API_BASE_URL = "https://api.openweathermap.org"

        private lateinit var instance: CityWeatherApp

        fun getInstance(): CityWeatherApp {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //We use Koin for dependency injection
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CityWeatherApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }

    }
}