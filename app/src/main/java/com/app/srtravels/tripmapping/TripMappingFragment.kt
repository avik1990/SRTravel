package com.app.srtravels.tripmapping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.MainActivity
import com.app.srtravels.R
import com.app.srtravels.bookinginputs.model.BookingInputs
import com.app.srtravels.databinding.FragmentTripMappingBinding
import com.app.srtravels.horizotalcalender.adapter.CalanderAdapter
import com.app.srtravels.horizotalcalender.model.Calenders
import com.app.srtravels.tripmapping.adapter.HeaderDayAdapter
import com.app.srtravels.tripmapping.adapter.TripImageAdapter
import com.app.srtravels.tripmapping.addroom.AddRoomFragment
import com.app.srtravels.tripmapping.addroom.model.RoomInputModel
import com.app.srtravels.tripmapping.model.Day
import com.app.srtravels.tripmapping.model.Hotel
import com.app.srtravels.tripmapping.model.ImageModel
import com.app.srtravels.util.IMAGE_PATH_URL
import com.app.srtravels.util.MAX_ADULT_PERSON_PER_ROOM
import com.app.srtravels.util.getFormattedDate_YYYYMMDD
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TripMappingFragment : Fragment(), HeaderDayAdapter.Interaction , CalanderAdapter.Interaction ,TripImageAdapter.Interaction {

    var bookingInputs: BookingInputs? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    var images_Place_Hotel: MutableList<ImageModel> = ArrayList()

    companion object {
        fun newInstance() = TripMappingFragment()
    }

    private lateinit var viewModel: TripMappingViewModel
    private var _binding: FragmentTripMappingBinding? = null
    private val binding get() = _binding!!

    private var previousSelect = 0
    private lateinit var adapter: TripImageAdapter

    private lateinit var contentadapter: HeaderDayAdapter
    private lateinit var calenderAdapter: CalanderAdapter
    private var startDate :String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookingInputs = arguments?.let { TripMappingFragmentArgs.fromBundle(it).bookinginput }
        bookingInputs.let { it ->
            (requireActivity() as MainActivity).binding.topBar.headerTitle.text = it?.destination
            var inputs = "From "+it?.startDate +" || "+it?.noOfAdults.toString() +"Adults(s) || "+ it?.noOfChilds.toString() +"Child(s)"
            (requireActivity() as MainActivity).binding.topBar.subText.text = inputs
            startDate = it?.startDate!!
            
            _binding?.tvRoomsRequired?.text = it.noOfAdults.toString() +" Adults(s) & " +
                    ""+ it.noOfChilds.toString() +" Child(s) in "+
                    (Math.round(it.noOfAdults.toDouble() / MAX_ADULT_PERSON_PER_ROOM)).toString() +" Room(s)"
        }
    }

    fun receiveData(data: String) {
        Toast.makeText(requireContext(),data,Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTripMappingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[TripMappingViewModel::class.java]
        viewModel.getTripPackageMappingData()

        _binding?.tvModifyRomms?.setOnClickListener {
            /*val action = TripMappingFragmentDirections.actionTripMappingFragmentToAddRoomFragment()
            findNavController().navigate(action)*/
            val fragmentA = AddRoomFragment()
            val transaction = childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, fragmentA, "fragment_a_tag")
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

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

    fun getRoomInputData(dataList: List<RoomInputModel>) {
        var gson = Gson()
        var mMineUserEntity = gson.toJson(dataList)
        Log.e("RecieveData1234" , mMineUserEntity)
    }

    private fun prepareMovieContentData(dataList: List<Day>) {
        images_Place_Hotel.clear()
        contentadapter = HeaderDayAdapter(requireContext(), this)
        _binding?.recyclerMain?.adapter = contentadapter
        _binding?.recyclerMain?.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        contentadapter.submitList(dataList)
        contentadapter.notifyDataSetChanged()

        //----------------------------------------------------
        images_Place_Hotel.clear()
        dataList.let {
            for (i in it.indices) {
                for (j in 0 until  it[i].Place.size) {
                    for(k in 0 until  it[i].Place[j].PlaceImages.size)
                        it[i].Place[j].PlaceImages[k].PlaceImage.let { place->
                            images_Place_Hotel.add(ImageModel(IMAGE_PATH_URL+place,"place",Math.random().toInt()))
                        }
                }
            }
        }

        dataList.let {
            for (i in it.indices) {
                for (j in 0 until  it[i].Hotel.size) {
                    for(k in 0 until  it[i].Hotel[j].HotelImages.size)
                        it[i].Hotel[j].HotelImages[k].HotelImages.let { hotel->
                            images_Place_Hotel.add(ImageModel(IMAGE_PATH_URL+hotel,"place",Math.random().toInt()))
                        }
                }
            }
        }

        setupSlider()
        //----------------------------------------------------
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

    private fun setupSlider() {
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = TripImageAdapter(requireContext(), this)
        _binding?.bannerSlider?.adapter = adapter
        _binding?.bannerSlider?.layoutManager = linearLayoutManager
        adapter.submitList(images_Place_Hotel)
        adapter.notifyDataSetChanged()
        PagerSnapHelper().attachToRecyclerView(_binding?.bannerSlider)
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

    override fun onItemSelected(position: Int, item: ImageModel) {
        Log.e("ff","fafa")
    }
}