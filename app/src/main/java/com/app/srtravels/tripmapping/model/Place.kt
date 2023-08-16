package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    val id: Int,
    val placeDesc: String,
    val placeImages: List<String>,
    val placeName: String
): Parcelable