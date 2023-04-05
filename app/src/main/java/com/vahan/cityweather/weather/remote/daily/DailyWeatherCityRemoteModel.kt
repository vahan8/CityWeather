package com.vahan.cityweather.weather.remote.daily

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vahan.cityweather.weather.remote.CoordinateRemoteModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class DailyWeatherCityRemoteModel(
    @Expose @SerializedName("coord") val coordinate: CoordinateRemoteModel,
    @Expose @SerializedName("id") val id: String,
    @Expose @SerializedName("name") val cityName: String
):Parcelable