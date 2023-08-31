package com.app.srtravels.tripmapping.addroom.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RoomInputModel(
    var id: Long = System.currentTimeMillis(),
    @Expose
    var roomName: String = "",
    @Expose
    var noOfAdults: Int = 0,
    @Expose
    var noOfChild: Int = 0,
    @Expose
    var childAgeRoomWise: @RawValue MutableList<ChildAgeLimit>? = mutableListOf()
) : Parcelable



