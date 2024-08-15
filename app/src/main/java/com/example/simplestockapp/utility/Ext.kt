package com.example.simplestockapp.utility

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long?.toDateString(format: String): String {
    if (this == null) return ""
    val date = Date(this)
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(date)
}

fun Long.toMillis(): Long {
    return this * 1000
}

fun Long?.toDate(): Date? {
    if (this == null) return null
    return Date(this * 1000)
}