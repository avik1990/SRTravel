package com.app.srtravels.trip

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.MainActivity
import com.app.srtravels.databinding.FragmentTripDetailsBinding
import com.app.srtravels.home.modules.trip.model.Trip
import com.app.srtravels.bookinginputs.BookingInputsFragment
import com.app.srtravels.trip.adapter.PackageCategoryAdapter
import com.app.srtravels.trip.model.Package
import com.app.srtravels.trip.model.PackageDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripFragmentDetails : Fragment(), PackageCategoryAdapter.Interaction {

    companion object {
        fun newInstance() = TripFragmentDetails()
    }

    private lateinit var contentadapter: PackageCategoryAdapter
    private var _binding: FragmentTripDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TripViewModel
    var trip: Trip? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trip = arguments?.let { TripFragmentDetailsArgs.fromBundle(it).trip }
        trip.let {
            (requireActivity() as MainActivity).binding.topBar.headerTitle.text = it?.title
            (requireActivity() as MainActivity).binding.topBar.subText.text = it?.description
        }

    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TripViewModel::class.java]
        prepareMovieContentData(viewModel.getTripData().packages)
    }

    private fun prepareMovieContentData(dataList: List<Package>) {
        contentadapter = PackageCategoryAdapter(requireContext(), this)
        _binding?.recyclerMain?.adapter = contentadapter
        _binding?.recyclerMain?.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList)
        contentadapter.notifyDataSetChanged()
    }

    override fun onItemSelectedHorizontal(position: Int, item: PackageDetail) {
        Toast.makeText(requireContext(),"Hello Brother",Toast.LENGTH_LONG).show()
        openBookingInputSheet()
        //findNavController().navigate(R.id.action_tripFragmentDetails_to_tripMappingFragment)
    }

    private fun openBookingInputSheet() {
        val bottomSheetFragment = BookingInputsFragment(trip)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

}