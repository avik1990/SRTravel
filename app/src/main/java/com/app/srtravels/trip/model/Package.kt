package com.app.srtravels.trip.model

data class Package(
    val packageCategory: String,
    val packageDesc: String,
    val packageDetails: List<PackageDetail>
)