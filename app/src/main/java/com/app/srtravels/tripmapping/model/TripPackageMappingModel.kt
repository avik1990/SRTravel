package com.app.srtravels.tripmapping.model

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
)