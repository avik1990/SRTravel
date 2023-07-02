package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Car(
    val carContact: Long,
    val carOwnerName: String,
    val carPrice: Int,
    val carType: String
): Parcelable