package com.app.srtravels.hotel.model

data class Room(
    val roomId: Int,
    val roomFacility: String,
    val roomImages: List<String>,
    val roomName: String,
    val roomWithACprice: Int,
    val roomWithDoubleBedPrice: Int,
    val roomWithOutAc: Int,
    val roomWithSingleBed: Int
)