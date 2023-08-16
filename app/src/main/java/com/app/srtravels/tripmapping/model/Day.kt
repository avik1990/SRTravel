package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Day(
    val dayDesc: String,
    val dayName: String,
    val hotels: List<Hotel>,
    val places: List<Place>,
    val routes: List<Route>
): Parcelable