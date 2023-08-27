package com.app.srtravels.tripmapping.addroom.model

import androidx.room.Ignore

data class ChildAgeLimit(
    var childId: Long = 0,
    var childAgeList: MutableList<Int> = mutableListOf(),
    var selectedChildAgeList: Int = -1,
    var isSelected: Boolean = false)