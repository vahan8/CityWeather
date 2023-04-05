package com.vahan.cityweather.weather

import com.vahan.cityweather.util.DateHelper
import com.vahan.cityweather.util.WeatherHelper
import com.vahan.cityweather.weather.local.CurrentWeatherEntity
import com.vahan.cityweather.weather.local.DailyWeatherEntity
import com.vahan.cityweather.weather.remote.CurrentWeatherRemoteModel
import com.vahan.cityweather.weather.remote.daily.DailyWeatherRemoteModel

object WeatherMapper {
    fun dailyWeatherFromRemoteModel(remoteModel: DailyWeatherRemoteModel): DailyWeather {
        return DailyWeather(
            DateHelper.getTimeInMillsFromUnixUtcTime(remoteModel.unixUtcTime),
            WeatherHelper.convertKelvinToCelsius(remoteModel.main.tempMin),
            WeatherHelper.convertKelvinToCelsius(remoteModel.main.tempMax)
        )
    }

    fun dailyWeatherFromLocalModel(localModel: DailyWeatherEntity): DailyWeather {
        return DailyWeather(
            localModel.date,
            localModel.minTemperature,
            localModel.maxTemperature
        )
    }

    fun currentWeatherFromRemoteModel(remoteModel: CurrentWeatherRemoteModel): CurrentWeather {
        return CurrentWeather(
            remoteModel.weather[0].description,
            WeatherHelper.convertKelvinToCelsius(remoteModel.main.temp)
        )
    }

    fun currentWeatherFromLocalModel(localModel: CurrentWeatherEntity): CurrentWeather {
        return CurrentWeather(
            localModel.weatherDescription,
            localModel.temperature
        )
    }

}