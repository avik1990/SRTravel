package com.app.srtravels.hotel

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.databinding.FragmentHotelBinding
import com.app.srtravels.hotel.adapter.HotelAdapter
import com.app.srtravels.hotel.model.Hotel
import com.app.srtravels.hotel.model.HotelModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelFragment : Fragment(), HotelAdapter.Interaction {

    companion object {
        fun newInstance() = HotelFragment()
    }

    private lateinit var contentadapter: HotelAdapter
    private lateinit var viewModel: HotelViewModel
    private var _binding: FragmentHotelBinding? = null
    private lateinit var hotel: Hotel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HotelViewModel::class.java]
        //hotel = HotelFragmentArgs.fromBundle(requireArguments()).hotel
        prepareMovieContentData(viewModel.getHotelData())
    }

    private fun prepareMovieContentData(dataList: HotelModels) {
        contentadapter = HotelAdapter(requireContext(), hotel.hotelId,this,)
        _binding?.hotelList?.adapter = contentadapter
        _binding?.hotelList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList.hotels)
        contentadapter.notifyDataSetChanged()
    }

    override fun onItemHotelSelected(position: Int, item: com.app.srtravels.hotel.model.Hotel) {
        Toast.makeText(context,item.hotelName,Toast.LENGTH_SHORT).show()
    }

}