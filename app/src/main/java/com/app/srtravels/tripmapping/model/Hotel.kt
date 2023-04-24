package com.app.srtravels.tripmapping.model

data class Hotel(
    val hotelImages: List<String>,
    val hotelLocation: String,
    val hotelName: String,
    val hotelRating: String,
    val rooms: List<Room>
)