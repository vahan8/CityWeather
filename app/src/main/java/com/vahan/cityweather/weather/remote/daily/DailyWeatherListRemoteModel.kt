package com.vahan.cityweather.weather.remote.daily

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DailyWeatherListRemoteModel(
    //@Expose @SerializedName("city") val city: DailyWeatherCityRemoteModel,
    @Expose @SerializedName("list") val list: List<DailyWeatherRemoteModel>
):Parcelable