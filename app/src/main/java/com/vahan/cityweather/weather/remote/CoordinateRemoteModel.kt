package com.vahan.cityweather.weather.remote

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoordinateRemoteModel(
    @Expose @SerializedName("lon") val lon: Double,
    @Expose @SerializedName("lat") val lat: Double
): Parcelable