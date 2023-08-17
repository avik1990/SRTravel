package com.app.srtravels.tripmapping.model

data class Place(
    val DayPackGuid: String,
    val DayPlaceGuid: String,
    val PlaceDesc: String,
    val PlaceGuid: String,
    val PlaceImages: List<PlaceImage>,
    val PlaceName: String
)