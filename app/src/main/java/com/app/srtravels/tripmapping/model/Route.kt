package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    val car: List<Car>,
    val routeName: String,
    val id: Int
): Parcelable