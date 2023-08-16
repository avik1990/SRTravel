package com.app.srtravels.tripmapping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.MainActivity
import com.app.srtravels.R
import com.app.srtravels.bookinginputs.model.BookingInputs
import com.app.srtravels.databinding.FragmentTripMappingBinding
import com.app.srtravels.horizotalcalender.HorizontalCalender
import com.app.srtravels.horizotalcalender.model.Calenders
import com.app.srtravels.tripmapping.adapter.HeaderDayAdapter
import com.app.srtravels.tripmapping.model.Day
import com.app.srtravels.tripmapping.model.Hotel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripMappingFragment : Fragment(), HeaderDayAdapter.Interaction , HorizontalCalender.OnItemSelectedListener {

     var bookingInputs: BookingInputs? = null
    companion object {
        fun newInstance() = TripMappingFragment()
    }

    interface onScrollRecyclerItems {
        fun onItemScrollPositions(position: Int)
    }

    private lateinit var viewModel: TripMappingViewModel
    private var _binding: FragmentTripMappingBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentadapter: HeaderDayAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingInputs = arguments?.let { TripMappingFragmentArgs.fromBundle(it).bookinginput }
        bookingInputs.let {
            (requireActivity() as MainActivity).binding.topBar.headerTitle.text = it?.destination
            var inputs = "From "+it?.startDate +" || "+it?.noOfAdults.toString() +"Adults(s) || "+ it?.noOfChilds.toString() +"Child(s)"
            (requireActivity() as MainActivity).binding.topBar.subText.text = inputs

            //Pass data to Calender Fragment
            val horizontalCalenderFragment = HorizontalCalender()
            val bundle = Bundle()
            horizontalCalenderFragment.listener = this
            bundle.putString( "startDate",it?.startDate)
            bundle.putInt( "days",5)
            horizontalCalenderFragment.arguments = bundle
            childFragmentManager.beginTransaction()
                .replace(R.id.trip_date_calender, horizontalCalenderFragment)
                .commit()
            ////////////
        }
    }

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
        _binding?.recyclerMain?.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList)
        contentadapter.notifyDataSetChanged()

        var currentItemPosition = RecyclerView.NO_POSITION
        binding?.recyclerMain?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstVisibleItemPosition = (_binding?.recyclerMain?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (firstVisibleItemPosition != RecyclerView.NO_POSITION && firstVisibleItemPosition != currentItemPosition) {
                    val itemView = (_binding?.recyclerMain?.layoutManager as LinearLayoutManager).findViewByPosition(firstVisibleItemPosition)
                    val itemViewTop = itemView?.top ?: 0

                    recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            val recyclerViewTop = recyclerView.top
                            val itemTopInRecyclerView = itemViewTop - recyclerViewTop
                            if (itemTopInRecyclerView > 0) {
                                Log.d("RecyclerView", "Item0 at position $firstVisibleItemPosition reached the top")
                            } else {

                                //Log.d("RecyclerView", "Item1 at position $firstVisibleItemPosition reached the top")
                            }
                            recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            currentItemPosition = firstVisibleItemPosition
                        }
                    })
                }
            }
        })
    }

    override fun onHotelChangeItem(position: Int, item: Hotel) {
        val action = TripMappingFragmentDirections.actionTripMappingFragmentToHotelFragment(item)
        findNavController().navigate(action)
    }

    override fun onCalenderItemSelected(position: Int, item: Calenders) {
        Toast.makeText(requireContext(), item.toString(), Toast.LENGTH_SHORT).show()
        //_binding?.recyclerMain?.scrollToPosition(position)
        (_binding?.recyclerMain?.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            position,
            0
        )
    }
}