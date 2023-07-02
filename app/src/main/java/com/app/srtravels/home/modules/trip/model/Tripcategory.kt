package com.app.srtravels.home.modules.trip.model

data class Tripcategory(
    val id: Int,
    val category: String,
    val trips: List<Trip>
)
