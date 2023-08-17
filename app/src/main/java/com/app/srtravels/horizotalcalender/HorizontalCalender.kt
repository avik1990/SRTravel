package com.app.srtravels.horizotalcalender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.databinding.FragmentHorizontalCalenderBinding
import com.app.srtravels.horizotalcalender.adapter.CalanderAdapter
import com.app.srtravels.horizotalcalender.model.Calenders
import com.app.srtravels.util.getFormattedDate_YYYYMMDD
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.Nullable

@AndroidEntryPoint
class HorizontalCalender : Fragment() ,  CalanderAdapter.Interaction {

    var  startDate: String? = null
    var noOfTripDays: Int = 0
    var listener: OnItemSelectedListener? = null
    //private val sharedViewModel: SharedViewModel by activityViewModels()

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


    fun getData(data: Int){
        //Toast.makeText(requireContext(), data.toString(), Toast.LENGTH_SHORT).show()
        if (previousSelect != data) {
            adapter.currentItemSelected = data
            adapter.notifyItemChanged(data)
            adapter.notifyItemChanged(previousSelect)
           // listener?.onCalenderItemSelected(position, item)
        }
        previousSelect = data
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HorizontalCalenderViewModel::class.java]
        //viewModel.generateCalenderDates("2023-08-28" ,noOfTripDays)
        viewModel.generateCalenderDates(getFormattedDate_YYYYMMDD(startDate!!) ,noOfTripDays)
        /*viewModel.sharedVariable.observe(viewLifecycleOwner) { newVal->
            Log.d("sfdfbsdhfbsdfbsd",newVal.toString())
            Toast.makeText(requireContext(),newVal.toString(),Toast.LENGTH_SHORT).show()
        }*/
        /*viewModel.sharedVariable.observe(viewLifecycleOwner) {
                value -> Toast.makeText(requireContext(), value.toString(), Toast.LENGTH_SHORT).show()
         }*/

        viewModel._calenderMutableData.observe(requireActivity()) { it ->
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

    }
}