package com.vahan.cityweather.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DailyWeather(
  val date: Long,
  val minTemperature: Double,
  val maxTemperature: Double
):Parcelable