package com.vahan.cityweather.weather.remote.daily

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vahan.cityweather.weather.remote.WeatherDescriptionRemoteModel
import com.vahan.cityweather.weather.remote.WeatherMainInfoRemoteModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class DailyWeatherRemoteModel(
    @Expose @SerializedName("dt") val unixUtcTime: Long,
    @Expose @SerializedName("weather") val weather: List<WeatherDescriptionRemoteModel>,
    @Expose @SerializedName("main") val main: WeatherMainInfoRemoteModel
   // @Expose @SerializedName("temp") val temp: DailyWeatherTemperatureRemoteModel
):Parcelable