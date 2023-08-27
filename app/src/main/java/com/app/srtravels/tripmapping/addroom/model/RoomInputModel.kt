package com.app.srtravels.tripmapping.addroom.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RoomInputModel(var id : Long =  System.currentTimeMillis(),
                          var roomName : String = "",
                          var isSelected : Boolean = true,
                          var noOfAdults: Int = 0,
                          var noOfChild: Int = 0,
                          var childAgeLimit: @RawValue MutableList<ChildAgeLimit> = mutableListOf(),
                          var selectedList:MutableList<Int>? =null) :Parcelable



