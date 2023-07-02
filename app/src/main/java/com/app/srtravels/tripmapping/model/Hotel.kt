package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hotel (
    val hotelId: Int,
    val hotelImages: List<String>,
    val hotelLocation: String,
    val hotelName: String,
    val hotelRating: String,
    val rooms: List<Room>
): Parcelable