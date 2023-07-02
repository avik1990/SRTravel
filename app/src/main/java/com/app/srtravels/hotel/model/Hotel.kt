package com.app.srtravels.hotel.model

data class Hotel(
    var hotelId: Int,
    var isSelected: Boolean = false,
    val hotelImages: List<String>,
    val hotelLocation: String,
    val hotelName: String,
    val hotelRating: String,
    val rooms: List<Room>
)