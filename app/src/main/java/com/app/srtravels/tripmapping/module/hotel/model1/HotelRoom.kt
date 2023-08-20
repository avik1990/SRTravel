package com.app.srtravels.tripmapping.module.hotel.model1

data class HotelRoom(
    val HotelRoomGuid: String,
    val HotelRoomImages: List<HotelRoomImage>,
    val HotelRoomName: String,
    val RoomFacilities: String
)