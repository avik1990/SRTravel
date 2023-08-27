package com.app.srtravels.tripmapping.addroom

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.srtravels.MainActivity
import com.app.srtravels.R
import com.app.srtravels.databinding.FragmentAddRoomBinding
import com.app.srtravels.tripmapping.TripMappingFragment
import com.app.srtravels.tripmapping.addroom.adapter.AddRoomAdapter
import com.app.srtravels.tripmapping.addroom.model.ChildAgeLimit
import com.app.srtravels.tripmapping.addroom.model.RoomInputModel
import com.app.srtravels.util.MAX_ADULT_PERSON_PER_ROOM
import com.app.srtravels.util.MAX_CHILD_AGE_LIMIT
import com.google.gson.Gson

class AddRoomFragment : Fragment(), AddRoomAdapter.Interaction {

    companion object {
        fun newInstance() = AddRoomFragment()
    }

    private lateinit var roomadapter: AddRoomAdapter
    private var _binding: FragmentAddRoomBinding? = null
    private val binding get() = _binding!!
    var selectChildAge: MutableList<Int> = ArrayList()

   // var selectChildAgeList: MutableList<ChildAgeLimit> = ArrayList()
   // var childAgeLimitList: MutableList<ChildAgeLimit> = ArrayList()
    var roomInputModelList: MutableList<RoomInputModel> = ArrayList()

    private lateinit var viewModel: AddRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).binding.topBar.headerTitle.text =
            "Select Rooms & Guests"
        (requireActivity() as MainActivity).binding.topBar.subText.setTextColor(resources.getColor(R.color.red))
        (requireActivity() as MainActivity).binding.topBar.subText.text = "Max 4 allowed in 1 Room"

        inflateAddRoomItem()
        _binding?.addAnotherRoom?.setOnClickListener {

            val roomInputs = RoomInputModel().copy(
                id = System.currentTimeMillis(),
                isSelected = true,
                noOfAdults = 1,
                noOfChild = 0,
            )
            roomInputModelList.add(roomInputs)
            roomadapter.submitList(roomInputModelList)
            roomadapter.notifyDataSetChanged()

        }

        _binding?.applyRequiredRooms?.setOnClickListener {
            (parentFragment as TripMappingFragment).getRoomInputData(roomInputModelList)
            parentFragmentManager.popBackStack()
        }
    }

    private fun inflateAddRoomItem() {
        val roomInputs = RoomInputModel().copy(
            id = System.currentTimeMillis(),
            isSelected = true,
            noOfAdults = 2,
            noOfChild = 0,
        )
        roomInputModelList.add(roomInputs)

        roomadapter = AddRoomAdapter(requireContext(), this)
        _binding?.recyclerAddRoom?.adapter = roomadapter
        _binding?.recyclerAddRoom?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        roomadapter.submitList(roomInputModelList)
        roomadapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddRoomViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCustomRoomSelection(position: Int, item: RoomInputModel, flag: String) {
        when (flag) {
            "delete" -> {
                roomInputModelList.removeAt(position)
                roomadapter.notifyItemRemoved(position)
                roomadapter.notifyItemRangeChanged(position, roomInputModelList.size)
            }

            "addNoOfAdults" -> {
                if (item.noOfAdults != MAX_ADULT_PERSON_PER_ROOM && item.noOfAdults < (MAX_ADULT_PERSON_PER_ROOM - item.noOfChild)) {
                    item.noOfAdults = item.noOfAdults + 1
                    roomadapter.notifyDataSetChanged()
                }
                if (item.noOfAdults == MAX_ADULT_PERSON_PER_ROOM) {
                    item.noOfChild = 0
                    roomadapter.notifyDataSetChanged()
                }
            }

            "removeNoOfAdults" -> {
                if (item.noOfAdults != 1) {
                    item.noOfAdults = item.noOfAdults - 1
                    roomadapter.notifyDataSetChanged()
                }
            }

            "addNoOfChild" -> {
                if (item.noOfChild < (MAX_ADULT_PERSON_PER_ROOM - item.noOfAdults)) {
                    item.noOfChild = item.noOfChild + 1
                    val childInputs = ChildAgeLimit()

                    item.childAgeLimit = item.childAgeLimit.plus(childInputs).toMutableList()
                    roomInputModelList[position] = item
                    roomadapter.submitList(roomInputModelList)
                    roomadapter.notifyDataSetChanged()
                }
            }

            "removeNoOfChild" -> {
                if (item.noOfChild != 0) {
                    item.noOfChild = item.noOfChild - 1

                    roomInputModelList[position].childAgeLimit.removeLast()
                    roomadapter.submitList(roomInputModelList)
                    roomadapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onAgeSelected(position: Int, childPosition: Int, age: Int) {
       // Log.e("POSITIONS", position +""+ childPosition  +""+age)
        roomInputModelList[position].childAgeLimit?.get(childPosition)?.selectedChildAgeList = age
        roomadapter.submitList(roomInputModelList)
        roomadapter.notifyDataSetChanged()
    }
}