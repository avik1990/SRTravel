package com.app.srtravels.tripmapping

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.R
import com.app.srtravels.databinding.FragmentTripDetailsBinding
import com.app.srtravels.databinding.FragmentTripMappingBinding
import com.app.srtravels.home.HomeFragmentDirections
import com.app.srtravels.hotel.HotelFragmentArgs
import com.app.srtravels.trip.adapter.PackageCategoryAdapter
import com.app.srtravels.trip.model.Package
import com.app.srtravels.tripmapping.adapter.HeaderDayAdapter
import com.app.srtravels.tripmapping.model.Day
import com.app.srtravels.tripmapping.model.Hotel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripMappingFragment : Fragment(), HeaderDayAdapter.Interaction {

    companion object {
        fun newInstance() = TripMappingFragment()
    }

    private lateinit var viewModel: TripMappingViewModel
    private var _binding: FragmentTripMappingBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentadapter: HeaderDayAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripMappingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TripMappingViewModel::class.java]
        viewModel.getTripPackageMappingData()
        prepareMovieContentData(viewModel.getTripPackageMappingData().Days)

    }

    private fun prepareMovieContentData(dataList: List<Day>) {
        contentadapter = HeaderDayAdapter(requireContext(), this)
        _binding?.recyclerMain?.adapter = contentadapter
        _binding?.recyclerMain?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList)
        contentadapter.notifyDataSetChanged()
    }

    override fun onHotelChangeItem(position: Int, item: Hotel) {
        //Toast.makeText(context,"Hello "+item.hotelName,Toast.LENGTH_SHORT).show()
        //findNavController().navigate(R.id.action_tripMappingFragment_to_hotelFragment)
       // val action = TripMappingFragmentDirections.actionTripMappingFragmentToHotelFragment(item)
       // findNavController().navigate(action)

        val action = TripMappingFragmentDirections.actionTripMappingFragmentToHotelFragment(item)
        findNavController().navigate(action)
    }
}