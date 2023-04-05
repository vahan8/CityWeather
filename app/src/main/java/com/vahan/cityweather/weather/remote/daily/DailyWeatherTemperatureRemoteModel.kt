package com.vahan.cityweather.weather.remote.daily

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DailyWeatherTemperatureRemoteModel(
    //Temperature is in Kelvins
    @Expose @SerializedName("day") val day: Double,
    @Expose @SerializedName("night") val night: Double,
    @Expose @SerializedName("eve") val evening: Double,
    @Expose @SerializedName("morn") val morning: Double,
    @Expose @SerializedName("min") val min: Double,
    @Expose @SerializedName("max") val max: Double
):Parcelable