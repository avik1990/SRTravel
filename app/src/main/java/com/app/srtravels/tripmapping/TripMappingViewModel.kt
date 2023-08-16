package com.app.srtravels.tripmapping

import android.content.Context
import androidx.lifecycle.ViewModel
import com.app.srtravels.tripmapping.model.TripPackageMappingModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class TripMappingViewModel@Inject constructor(
    private val context: Context
) : ViewModel() {

    fun getTripPackageMappingData(): TripPackageMappingModel {
        val gson = Gson()
        val i: InputStream = context.assets.open("packmapping.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: TripPackageMappingModel = gson.fromJson(br, TripPackageMappingModel::class.java)
        return dataList
    }


}