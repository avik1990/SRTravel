package com.app.srtravels.horizotalcalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.databinding.FragmentHorizontalCalenderBinding
import com.app.srtravels.horizotalcalender.adapter.CalanderAdapter
import com.app.srtravels.horizotalcalender.model.Calenders
import com.app.srtravels.util.getFormattedDate_YYYYMMDD

class HorizontalCalender : Fragment() ,  CalanderAdapter.Interaction {

    var  startDate: String? = null
    var noOfTripDays: Int = 0
    var listener: OnItemSelectedListener? = null

    companion object {
        fun newInstance() = HorizontalCalender()
    }

    interface OnItemSelectedListener {
        fun onCalenderItemSelected(position: Int, item: Calenders)
    }

    private lateinit var viewModel: HorizontalCalenderViewModel
    private var _binding: FragmentHorizontalCalenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CalanderAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var previousSelect = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHorizontalCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HorizontalCalenderViewModel::class.java]
        //viewModel.generateCalenderDates("2023-08-28" ,noOfTripDays)
        viewModel.generateCalenderDates(getFormattedDate_YYYYMMDD(startDate!!) ,noOfTripDays)

        viewModel._calenderMutableData.observe(requireActivity()){ it ->
            linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = CalanderAdapter(requireContext(), this)
            _binding?.calenderDate?.adapter = adapter
            _binding?.calenderDate?.layoutManager = linearLayoutManager
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onItemSelected(position: Int, item: Calenders) {
        if (previousSelect != position) {
            adapter.currentItemSelected = position
            adapter.notifyItemChanged(position)
            adapter.notifyItemChanged(previousSelect)
            listener?.onCalenderItemSelected(position, item)
        }
        previousSelect = position
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            startDate = it.getString("startDate")
            noOfTripDays = it.getInt("days")

        }



       /* parentFragmentManager.setFragmentResultListener(
            "data", this
        ) { requestKey: String, result: Bundle ->
            if (requestKey == "data") {
                val receivedData = result.getString("listItemPosition")
                Toast.makeText(requireContext(),"Hello "+receivedData,Toast.LENGTH_SHORT).show()
                // Process the received data here
            }
        }*/
    }
}