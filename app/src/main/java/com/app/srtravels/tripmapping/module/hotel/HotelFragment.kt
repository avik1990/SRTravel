package com.app.srtravels.tripmapping.module.hotel

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.databinding.FragmentHotelBinding
import com.app.srtravels.tripmapping.module.hotel.adapter.HotelAdapter
import com.app.srtravels.tripmapping.module.hotel.adapter.HotelRoomAdapter
import com.app.srtravels.tripmapping.module.hotel.model1.Hotel

import com.app.srtravels.tripmapping.module.hotel.model1.HotelM
import com.app.srtravels.tripmapping.module.hotel.model1.HotelRoom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelFragment : Fragment(), HotelAdapter.Interaction{

    companion object {
        fun newInstance() = HotelFragment()
    }

    private lateinit var contentadapter: HotelAdapter
    private lateinit var viewModel: HotelViewModel
    private var _binding: FragmentHotelBinding? = null
    //hotelGuid from previous TripMappingFragment
    private lateinit var hotelIdPrevious: String
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HotelViewModel::class.java]
        hotelIdPrevious = HotelFragmentArgs.fromBundle(requireArguments()).hotelGuid
        prepareMovieContentData(viewModel.getHotelData())
    }

    private fun prepareMovieContentData(dataList: HotelM) {
        contentadapter = HotelAdapter(requireContext(), hotelIdPrevious,this,)
        _binding?.hotelList?.adapter = contentadapter
        _binding?.hotelList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList.data.Hotel)
        contentadapter.notifyDataSetChanged()
    }

    override fun onItemHotelSelected(position: Int, item: Hotel) {
        Toast.makeText(context,item.HotelName,Toast.LENGTH_SHORT).show()
    }

    override fun onItemHotelRoomSelected(position: Int, item: HotelRoom) {
        Toast.makeText(context,"Oh YEah"+ item.HotelRoomName,Toast.LENGTH_SHORT).show()

    }


}