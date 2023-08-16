package com.app.srtravels.horizotalcalender

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.app.srtravels.horizotalcalender.model.Calenders
import java.util.Locale

class HorizontalCalenderViewModel : ViewModel() {

    public var _calenderMutableData: MutableLiveData<MutableList<Calenders>> = MutableLiveData()
    val listCalenderData = mutableListOf<Calenders>()

    fun generateCalenderDates(selectedDate: String, noOfDays: Int) {

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val startDate: Date = sdf.parse(selectedDate)
        val calendar = Calendar.getInstance()
        calendar.time = startDate
        listCalenderData.clear()
            for (i in 0 until noOfDays) {
                val currentDate = calendar.time
                var datecomponent = sdf.format(currentDate).split("-")
                listCalenderData.add(Calenders().apply {
                    id = i+1
                    day = datecomponent[2].toInt()
                    month = SimpleDateFormat("MMM", Locale.getDefault()).format(currentDate)
                    year = datecomponent[0].toInt()
                    fullDate = sdf.format(currentDate)
                    dayText = SimpleDateFormat("EEE", Locale.getDefault()).format(currentDate)
                })
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

        _calenderMutableData.value = listCalenderData
    }

}