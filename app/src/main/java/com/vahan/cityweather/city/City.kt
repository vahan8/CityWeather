package com.vahan.cityweather.city

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    @Expose @SerializedName("country") val country: String,
    @Expose @SerializedName("lat") val lat: Double,
    @Expose @SerializedName("lon") val lon: Double,
    @Expose @SerializedName("name") val name: String
):Parcelable