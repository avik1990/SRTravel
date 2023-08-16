package com.app.srtravels.home.modules.trip

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.srtravels.home.modules.trip.model.TripDataModel
import com.app.srtravels.home.modules.trip.model.Tripcategory
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class TripsViewModel@Inject constructor(
    private val context: Context
) : ViewModel() {

    // var tripList: List<TripDataModel> = ArrayList()
    // private val _responseTripCategory: MutableLiveData<TripDataModel> = MutableLiveData()
    // val responeTripCategory: LiveData<TripDataModel> = _responseTripCategory

    fun getTripData(): List<Tripcategory> {
        val gson = Gson()
        val i: InputStream = context.assets.open("tripcategory.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: TripDataModel = gson.fromJson(br, TripDataModel::class.java)
        return dataList.tripcategory
    }
}
