package com.sparklead.linkinfo.common.utils

import java.text.SimpleDateFormat
import java.util.Calendar

object DateUtils {

    fun overviewSpan(start: String, end: String): String {
        val dateFormat = SimpleDateFormat("dd MMM")
        val calendar = Calendar.getInstance()
        val startDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endDate = dateFormat.format(calendar.time)

        return "$startDate - $endDate"
    }

    fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hourOfDay) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

}