package com.app.srtravels.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


fun convertdate_DDMMMYYYY(date: String) : Date{
    //val dtStart = "2021-06-30"
    val format = SimpleDateFormat("dd, MMM yyyy")
    val date = format.parse(date)
    Log.e("date", "date: $date")
    return date
}

fun getFormattedDate_DDMMMYYYY(normal_date: String): String? {
    var formated_date = ""
    if (normal_date.length > 6) {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH)
        val targetFormat = java.text.SimpleDateFormat("dd, MMM yyyy")
        val date: java.util.Date
        try {
            date = originalFormat.parse(normal_date)
            formated_date = targetFormat.format(date) // 20120821
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    } else {
        formated_date = normal_date
    }
    return formated_date
}


fun getFormattedDate_YYYYMMDD(normal_date: String): String {
    var formated_date = ""
    if (normal_date.length > 6) {
        val originalFormat = SimpleDateFormat("dd, MMM yyyy", java.util.Locale.ENGLISH)
        val targetFormat = java.text.SimpleDateFormat("yyyy-MM-dd")
        val date: java.util.Date
        try {
            date = originalFormat.parse(normal_date)
            formated_date = targetFormat.format(date) // 20120821
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    } else {
        formated_date = normal_date
    }
    return formated_date
}