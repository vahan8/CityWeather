package com.vahan.cityweather.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val DATE_FORMAT = "dd MMM yyyy h aa"

    private val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)

    fun getTimeInMillsFromUnixUtcTime(utcTime: Long): Long {
        val offset = TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings
        return utcTime * 1000 - offset
    }

    fun getFormattedDate(mills: Long): String{
        val date = Date(mills)
        return dateFormat.format(date)
    }
}