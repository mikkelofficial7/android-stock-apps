package com.example.simplestockapp

import com.example.simplestockapp.utility.Constant
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTimeUnitTest {
    @Test
    fun dateTimeString() {
        printDateFormat(Constant.DATE_FORMAT, 1723680540, false)
        printDateFormat(Constant.DATE_FORMAT_2, 1723680540, false)
        printDateFormat(Constant.DATE_FORMAT, toMillis(1723680540), true)
        printDateFormat(Constant.DATE_FORMAT_2, toMillis(1723680540), true)
    }

    private fun printDateFormat(format: String, timestamp: Long?, isAssertValid: Boolean) {
        println("Date convert to string result=${formatDateToString(format, timestamp)} => $isAssertValid")
    }

    private fun toMillis(timestamp: Long?): Long {
        return (timestamp ?: 0) * 1000
    }

    private fun formatDateToString(format: String, timestamp: Long?) : String{
        if (timestamp == null) return ""
        val date = Date(timestamp)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }
}