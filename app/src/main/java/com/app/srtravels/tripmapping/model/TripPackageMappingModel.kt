package com.app.srtravels.tripmapping.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripPackageMappingModel(
    val Days: List<Day>,
    val packageCars: Int,
    val packageDays: String,
    val packageDiscountedPrice: Int,
    val packageEndDate: String,
    val packageHotels: Int,
    val packageId: Int,
    val packageName: String,
    val packageOfferedPrice: Int,
    val packageStartDate: String,
    val packageThumbnail: String
): Parcelable