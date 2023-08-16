package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(
    val id: Int,
    val roomFacility: String,
    val roomImages: List<String>,
    val roomName: String,
    val roomWithACprice: Int,
    val roomWithDoubleBedPrice: Int,
    val roomWithOutAc: Int,
    val roomWithSingleBed: Int
): Parcelable