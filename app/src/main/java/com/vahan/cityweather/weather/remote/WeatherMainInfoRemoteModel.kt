package com.vahan.cityweather.weather.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class WeatherMainInfoRemoteModel(
    @Expose @SerializedName("temp") val temp: Double,
    @Expose @SerializedName("feels_like") val feelsLike: Double,
    @Expose @SerializedName("temp_min") val tempMin: Double,
    @Expose @SerializedName("temp_max") val tempMax: Double,
    @Expose @SerializedName("pressure") val pressure: Double,
    @Expose @SerializedName("humidity") val humidity: Double
): Parcelable