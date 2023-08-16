package com.app.srtravels.bookinginputs.model

import android.os.Parcelable
import com.app.srtravels.home.modules.trip.model.Trip
import kotlinx.android.parcel.Parcelize

@Parcelize
 class BookingInputs : Parcelable{

     lateinit  var trip_guid: String
     lateinit  var package_guid:String
     lateinit var startPoint:String
     lateinit var destination:String
     lateinit var startDate: String
     var noOfAdults: Int = 0
     var noOfChilds: Int = 0
     var childAgeList: MutableList<Int> ? = null

     override fun toString(): String {
         return "BookingInputs(trip_guid='$trip_guid', package_guid='$package_guid', startPoint='$startPoint', destination='$destination', startDate='$startDate', noOfAdults='$noOfAdults', noOfChilds='$noOfChilds', childAgeList=$childAgeList)"
     }
 }