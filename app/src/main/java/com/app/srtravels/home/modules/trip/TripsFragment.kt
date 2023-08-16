package com.app.srtravels.home.modules.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.databinding.FragmentTripsBinding
import com.app.srtravels.home.HomeFragmentDirections
import com.app.srtravels.home.modules.trip.adapter.TripCategoryAdapter
import com.app.srtravels.home.modules.trip.model.Trip
import com.app.srtravels.home.modules.trip.model.Tripcategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripsFragment : Fragment(), TripCategoryAdapter.Interaction {

    companion object {
        fun newInstance() = TripsFragment()
    }

    private lateinit var contentadapter: TripCategoryAdapter
    private lateinit var viewModel: TripsViewModel
    private var _binding: FragmentTripsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TripsViewModel::class.java]
        prepareMovieContentData(viewModel.getTripData())
    }

    private fun prepareMovieContentData(dataList: List<Tripcategory>) {
        contentadapter = TripCategoryAdapter(requireContext(), this)
        _binding?.recyclerMain?.adapter = contentadapter
        _binding?.recyclerMain?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList)
        contentadapter.notifyDataSetChanged()
    }

    override fun onItemSelectedHorizontal(position: Int, item: Trip) {
        item.let {
            val direction = HomeFragmentDirections.actionHomeFragmentToTripFragmentDetails2(it)
            findNavController().navigate(direction)
        }
    }
}
