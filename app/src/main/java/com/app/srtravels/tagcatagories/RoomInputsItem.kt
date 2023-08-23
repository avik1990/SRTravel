package com.app.srtravels.tagcatagories

data class RoomInputsItem(
    val boolean: Boolean,
    val childAgeList: List<Any>,
    val id: Long,
    val noOfAdults: Int,
    val noOfChild: Int,
    val roomName: String
)

data class TotalCounts(
    val totalAdults: Int,
    val totalChilds: Int,
    val totalRooms: Int,
    val roomInputsItem: List<RoomInputsItem>
)