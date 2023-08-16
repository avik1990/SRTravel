package com.app.srtravels.hotel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.srtravels.hotel.model.HotelModels
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class HotelViewModel@Inject constructor(
    private val context: Context
) : ViewModel() {

    fun getHotelData(): HotelModels {
        val gson = Gson()
        val i: InputStream = context.assets.open("hotel.json")
        val br = BufferedReader(InputStreamReader(i))
        return gson.fromJson(br, HotelModels::class.java)
    }
}