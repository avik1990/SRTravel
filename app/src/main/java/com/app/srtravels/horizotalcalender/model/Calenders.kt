package com.app.srtravels.horizotalcalender.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Calenders  : Parcelable {

     var id: Int = 0
     var day: Int = 0
     lateinit var month: String
     var year: Int = 0
     lateinit var fullDate:  String
     lateinit var dayText:  String

    override fun toString(): String {
        return "Calenders(day=$day, month=$month, year=$year)"
    }


}