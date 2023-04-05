package com.vahan.cityweather.util

object WeatherHelper {
    fun convertKelvinToCelsius(kelvin: Double): Double {
        return kelvin.minus(273.15)
    }

}