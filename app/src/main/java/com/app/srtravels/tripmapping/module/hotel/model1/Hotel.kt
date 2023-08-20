package com.app.srtravels.tripmapping.module.hotel.model1

data class Hotel(
    val DayHotelGuid: String,
    val DayPackGuid: String,
    val HotelGuid: String,
    val HotelImages: List<HotelImage>,
    val HotelLocation: String,
    val HotelName: String,
    val HotelRating: String,
    val HotelRooms: List<HotelRoom>
)