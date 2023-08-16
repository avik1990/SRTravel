package com.app.srtravels.home.modules.trip.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trip(
    val id: Int,
    val description: String,
    val startingPrice: String,
    val thumb: String,
    val title: String
): Parcelable
