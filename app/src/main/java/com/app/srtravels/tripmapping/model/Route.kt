package com.app.srtravels.tripmapping.model

data class Route(
    val Cars: List<Car>,
    val DayPackGuid: String,
    val DayRouteGuid: String,
    val RouteEndName: String,
    val RouteGuid: String,
    val RouteStartName: String
)