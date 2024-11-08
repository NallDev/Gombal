package com.nalldev.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonHelper {
    fun convertTimestampToDate(timestamp: Int?): String {
        if (timestamp == null) return "-"
        val date = Date(timestamp.toLong() * 1000L)
        val format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return format.format(date)
    }
}