package com.app.srtravels.tripmapping.model

data class Data(
    val Day: List<Day>,
    val PackageDays: Int,
    val PackageEndDate: Any,
    val PackageName: String,
    val PackageNights: Int,
    val PackageOfferedPrice: Any,
    val PackageStartDate: Any,
    val packageDiscountedPrice: Any
)