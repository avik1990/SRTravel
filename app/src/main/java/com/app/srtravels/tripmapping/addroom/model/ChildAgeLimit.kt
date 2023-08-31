package com.app.srtravels.tripmapping.addroom.model

import com.app.srtravels.util.MAX_CHILD_AGE_LIMIT
import com.google.gson.annotations.Expose

data class ChildAgeLimit(
    var childAgeList: MutableList<Int>? = (1..MAX_CHILD_AGE_LIMIT).toMutableList(),
    @Expose
    var selectedChildAge: Int = -1
)