package com.app.srtravels.slider

import androidx.lifecycle.ViewModel
import com.app.srtravels.slider.model.BannerModel

class SliderViewModel : ViewModel() {

    var listPagerData: MutableList<BannerModel> = ArrayList()

    fun getSLiderImages(): List<BannerModel> {
        listPagerData.clear()
        listPagerData.add(BannerModel("https://images.pexels.com/photos/3278215/pexels-photo-3278215.jpeg?auto=compress&cs=tinysrgb&w=600", "1", "2"))
        listPagerData.add(BannerModel("https://images.pexels.com/photos/590029/pexels-photo-590029.jpeg?auto=compress&cs=tinysrgb&w=600", "1", "2"))
        listPagerData.add(BannerModel("https://images.pexels.com/photos/24698/pexels-photo-24698.jpg?auto=compress&cs=tinysrgb&w=600", "1", "2"))
        listPagerData.add(BannerModel("https://images.pexels.com/photos/36487/above-adventure-aerial-air.jpg?auto=compress&cs=tinysrgb&w=600", "1", "2"))
        listPagerData.add(BannerModel("https://images.pexels.com/photos/933054/pexels-photo-933054.jpeg?auto=compress&cs=tinysrgb&w=600", "1", "2"))
        listPagerData.add(BannerModel("https://images.pexels.com/photos/4091975/pexels-photo-4091975.jpeg?auto=compress&cs=tinysrgb&w=600", "1", "2"))
        return listPagerData
    }
}
