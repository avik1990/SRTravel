package com.app.srtravels.trip

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.srtravels.home.modules.trip.model.TripDataModel
import com.app.srtravels.trip.model.TripModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class TripViewModel@Inject constructor(
    private val context: Context
) : ViewModel() {

    fun getTripData(): TripModel {
        val gson = Gson()
        val i: InputStream = context.assets.open("trip.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: TripModel = gson.fromJson(br, TripModel::class.java)
        return dataList
    }


}