package com.app.srtravels.tripmapping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.MainActivity
import com.app.srtravels.bookinginputs.model.BookingInputs
import com.app.srtravels.databinding.FragmentTripMappingBinding
import com.app.srtravels.horizotalcalender.adapter.CalanderAdapter
import com.app.srtravels.horizotalcalender.model.Calenders
import com.app.srtravels.tripmapping.adapter.HeaderDayAdapter
import com.app.srtravels.tripmapping.model.Day
import com.app.srtravels.tripmapping.model.Hotel
import com.app.srtravels.util.MAX_ADULT_PERSON_PER_ROOM
import com.app.srtravels.util.getFormattedDate_YYYYMMDD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripMappingFragment : Fragment(), HeaderDayAdapter.Interaction , CalanderAdapter.Interaction  {

    var bookingInputs: BookingInputs? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun newInstance() = TripMappingFragment()
    }

    private lateinit var viewModel: TripMappingViewModel
    private var _binding: FragmentTripMappingBinding? = null
    private var previousSelect = 0
    private val binding get() = _binding!!
    private lateinit var contentadapter: HeaderDayAdapter
    private lateinit var calenderAdapter: CalanderAdapter
    private var startDate :String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingInputs = arguments?.let { TripMappingFragmentArgs.fromBundle(it).bookinginput }
        bookingInputs.let {
            (requireActivity() as MainActivity).binding.topBar.headerTitle.text = it?.destination
            var inputs = "From "+it?.startDate +" || "+it?.noOfAdults.toString() +"Adults(s) || "+ it?.noOfChilds.toString() +"Child(s)"
            (requireActivity() as MainActivity).binding.topBar.subText.text = inputs
            startDate = it?.startDate!!
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

        ////for Calender
        viewModel.generateCalenderDates(getFormattedDate_YYYYMMDD(startDate) ,6)
        viewModel._calenderMutableData.observe(requireActivity()) { it ->
            linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            calenderAdapter = CalanderAdapter(requireContext(), this)
            _binding?.calenderDate?.adapter = calenderAdapter
            _binding?.calenderDate?.layoutManager = linearLayoutManager
            calenderAdapter.submitList(it)
            calenderAdapter.notifyDataSetChanged()
        }
        prepareMovieContentData(viewModel.getTripPackageMappingData().data.Day)
    }

    private fun prepareMovieContentData(dataList: List<Day>) {
        contentadapter = HeaderDayAdapter(requireContext(), this)
        _binding?.recyclerMain?.adapter = contentadapter
        _binding?.recyclerMain?.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList)
        contentadapter.notifyDataSetChanged()

        ////Get the first element visible in the screen while scroll in recyclerview
        var currentItemPosition = RecyclerView.NO_POSITION
        binding?.recyclerMain?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItemPosition != RecyclerView.NO_POSITION && firstVisibleItemPosition != currentItemPosition) {
                    val itemView = layoutManager.findViewByPosition(firstVisibleItemPosition)
                    val itemTopInRecyclerView = itemView?.top ?: 0

                    if (itemTopInRecyclerView == 0) {
                        Log.d("RecyclerView", "Item at position $firstVisibleItemPosition reached the top")
                    } else {
                        (_binding?.calenderDate?.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                            firstVisibleItemPosition,
                            0
                        )
                        if (previousSelect != firstVisibleItemPosition) {
                            calenderAdapter.currentItemSelected = firstVisibleItemPosition
                            calenderAdapter.notifyItemChanged(firstVisibleItemPosition)
                            calenderAdapter.notifyItemChanged(previousSelect)
                        }
                        previousSelect = firstVisibleItemPosition
                    }
                    currentItemPosition = firstVisibleItemPosition
                }
            }
        })
    }

    override fun onHotelChangeItem(position: Int, item: Hotel) {
        val action = TripMappingFragmentDirections.actionTripMappingFragmentToHotelFragment(item.HotelGuid)
        findNavController().navigate(action)
    }

    override fun onItemSelected(position: Int, item: Calenders) {
        if (previousSelect != position) {
            calenderAdapter.currentItemSelected = position
            calenderAdapter.notifyItemChanged(position)
            calenderAdapter.notifyItemChanged(previousSelect)
            (_binding?.recyclerMain?.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                position,
                0)
        }
        previousSelect = position
    }
}