package com.vahan.cityweather.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.vahan.cityweather.app.CityWeatherApp

object ConnectivityHelper {

    fun hasInternetConnection(): Boolean {
        val connectivityManager = CityWeatherApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) or
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) or
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

    }

}