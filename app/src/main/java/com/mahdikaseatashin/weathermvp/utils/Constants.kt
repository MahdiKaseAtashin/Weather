package com.mahdikaseatashin.weathermvp.utils

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    private const val TIME_PATTERN = "HH:mm"
    const val METRIC_UNIT = "metric"
    const val API_KEY = "f8b2050fdc575a32a2b7afd4d389e663"


    fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy, MMMM, dd hh:mm aaa", Locale.getDefault())
        val date = dateFormat.format(calender.time)
        return date.toString()
    }

    fun convertTime(time: Long): String? {
        val date = Date(time * 1000L)
        val sdf = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

}
