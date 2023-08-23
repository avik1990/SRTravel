package com.app.srtravels.tripmapping.addroom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*@Parcelize
class RoomInputModel : Parcelable {
    var id : Long =  System.currentTimeMillis()
    lateinit  var roomName : String
    var boolean : Boolean = true
    var noOfAdults: Int = 0
    var noOfChild: Int = 0
    var childAgeList: MutableList<Int> ? = null
}*/

@Parcelize
data class RoomInputModel(var id : Long =  System.currentTimeMillis(), var roomName : String ="",
    var isSelected : Boolean = true,
    var noOfAdults: Int = 0,
    var noOfChild: Int = 0,
    var childAgeList: MutableList<Int> ? = null) :Parcelable



