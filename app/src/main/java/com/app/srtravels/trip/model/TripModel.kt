package com.app.srtravels.trip.model

data class TripModel(
    val packages: List<Package>,
    val tripDesc: String,
    val tripId: Int,
    val tripImages: List<String>,
    val tripName: String
)