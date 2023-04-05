package com.vahan.cityweather.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CurrentWeather(
  val weatherDescription: String,
  val temperature: Double
):Parcelable