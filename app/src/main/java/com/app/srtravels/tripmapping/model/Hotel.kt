package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Hotel(
    val DayHotelGuid: String,
    val DayPackGuid: String,
    val HotelGuid: String,
    val HotelImages: @RawValue List<HotelImage>,
    val HotelLocation: String,
    val HotelName: String,
    val HotelRating: String,
    val HotelRooms: @RawValue List<HotelRoom>
) : Parcelable