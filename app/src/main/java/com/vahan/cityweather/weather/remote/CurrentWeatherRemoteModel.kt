package com.vahan.cityweather.weather.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class CurrentWeatherRemoteModel(
    @Expose @SerializedName("coord") val coordinate: CoordinateRemoteModel,
    @Expose @SerializedName("weather") val weather: List<WeatherDescriptionRemoteModel>,
    @Expose @SerializedName("main") val main: WeatherMainInfoRemoteModel,
    @Expose @SerializedName("name") val cityName: String
):Parcelable