/*
package com.app.srtravels.horizotalcalender

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.srtravels.horizotalcalender.model.Calenders
import com.app.srtravels.tripmapping.model.TripPackageMappingModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SharedViewModel@Inject constructor(
    private val context: Context
): ViewModel() {

    var _calenderMutableData: MutableLiveData<MutableList<Calenders>> = MutableLiveData()
    val listCalenderData = mutableListOf<Calenders>()

   // private val _sharedVariable = MutableLiveData<Int>()
  ///  val sharedVariable: LiveData<Int> = _sharedVariable

   // fun updateSharedVariable(newValue: Int) {
    //    _sharedVariable.value = newValue
  //  }

    fun getTripPackageMappingData(): TripPackageMappingModel {
        val gson = Gson()
        val i: InputStream = context.assets.open("packmapping.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: TripPackageMappingModel = gson.fromJson(br, TripPackageMappingModel::class.java)
        return dataList
    }

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
                id = i + 1
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
}*/
