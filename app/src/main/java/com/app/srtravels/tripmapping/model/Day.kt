package com.app.srtravels.tripmapping.model

data class Day(
    val DayGuid: String,
    val DayName: String,
    val Hotel: List<Hotel>,
    val Place: List<Place>,
    val Route: List<Route>
)