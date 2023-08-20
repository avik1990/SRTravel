package com.app.srtravels.bookinginputs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.srtravels.R
import com.app.srtravels.home.modules.trip.model.Trip
import com.app.srtravels.bookinginputs.model.BookingInputs
import com.app.srtravels.databinding.FragmentBookinginputsBinding
import com.app.srtravels.trip.TripFragmentDetailsDirections
import com.app.srtravels.util.MAX_PERSON_PER_TRIP
import com.app.srtravels.util.getFormattedDate_DDMMMYYYY
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class BookingInputsFragment(trip: Trip?) : BottomSheetDialogFragment() {

    var adultList: MutableList<String> = ArrayList()
    var childList: MutableList<String> = ArrayList()
    var selectChildAge: MutableList<Int> = ArrayList()

    val calendar= Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var spNoOfAdults = 0
    var spNoOfChild = 0
    var selectedNoofChild = 0
    var triplocal: Trip? = null

    init {
        this.triplocal = trip
    }
    companion object {
        fun newInstance(trip: Trip?): BookingInputsFragment {
            return BookingInputsFragment(trip)
        }
    }

    private lateinit var viewModel: BookingInputsViewModel
    private var _binding: FragmentBookinginputsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookinginputsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[BookingInputsViewModel::class.java]

        binding.startDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                binding.startDate.text = getFormattedDate_DDMMMYYYY(year.toString() +"-"+ (monthOfYear+1) + "-" + dayOfMonth)
            }, year, month, day)
            datePickerDialog.show()
        }

        binding.tvChildPolicy.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.child_policy_bottomsheet, null)
            val btnClose = view.findViewById<ImageView>(R.id.btnCancel)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.setContentView(view)
            if(dialog.window != null)
                dialog.window!!.setDimAmount(0F);
            dialog.show()
        }

        // for adult dropdown list
        adultList.add("Select")
        for (i in 0 until MAX_PERSON_PER_TRIP) {
            adultList.add((i+1).toString())
        }

        binding.btnNext.setOnClickListener {
            Toast.makeText(context,selectChildAge.toString(),Toast.LENGTH_SHORT).show()
            var bookingInputs = BookingInputs().apply {
                trip_guid = triplocal?.id.toString()
                package_guid = "asdasdsad"
                startPoint = "Thakurpukur"
                destination = triplocal?.title.toString()
                startDate = binding.startDate.text.toString()
                noOfAdults= spNoOfAdults
                noOfChilds =  selectedNoofChild
                childAgeList = selectChildAge
            }

            bookingInputs.let {

                if(bookingInputs.startPoint.isBlank()){
                    Toast.makeText(context,"Enter Starting Point",Toast.LENGTH_SHORT).show()
                    return@let
                }
                if(bookingInputs.destination.isBlank()){
                    Toast.makeText(context,"Enter Destination",Toast.LENGTH_SHORT).show()
                    return@let
                }
                if(bookingInputs.startDate.isEmpty() || bookingInputs.startDate.isBlank()){
                    Toast.makeText(context,"Enter Start Date",Toast.LENGTH_SHORT).show()
                    return@let
                }
                if(bookingInputs.noOfAdults == 0){
                    Toast.makeText(context,"Enter Adult Count",Toast.LENGTH_SHORT).show()
                    return@let
                }

                if(bookingInputs.noOfChilds != selectChildAge.size){
                    Toast.makeText(context,"Enter Children Age.Please Comply with Child Policy",Toast.LENGTH_SHORT).show()
                    return@let
                }

                if(bookingInputs.noOfChilds == 0) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Confirm!!")
                    builder.setMessage("Are you sure there are no person below 12 years of age")
                   // builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("Yes, I Do have kids"){
                            dialogInterface, which ->
                        //dialog?.dismiss()
                    }
                    builder.setNegativeButton("No"){ dialogInterface, which ->
                        dialog?.dismiss()
                        bookingInputs.let {
                         val direction = TripFragmentDetailsDirections.actionTripFragmentDetailsToTripMappingFragment(bookingInputs)
                            findNavController().navigate(direction)
                        }
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }else{
                    val direction = TripFragmentDetailsDirections.actionTripFragmentDetailsToTripMappingFragment(bookingInputs)
                    findNavController().navigate(direction)
                }
                Log.e("SelectedData" , bookingInputs.toString())

            }
        }

        val adapter: ArrayAdapter<String> =
        ArrayAdapter<String>(requireContext(), R.layout.spinner_list, adultList)
        binding.spnAdult.adapter = adapter
        binding.spnAdult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position>0) {
                     spNoOfAdults = binding.spnAdult.selectedItem.toString().toInt()
                     spNoOfChild = MAX_PERSON_PER_TRIP - spNoOfAdults

                    if(spNoOfAdults >= 14) {
                        binding.childView.visibility = View.GONE
                    }else{
                        feedChildSpinner()
                        binding.childView.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun feedChildSpinner() {
        childList.clear()
        childList.add("Select")
        for (i in 0 until spNoOfChild) {
            childList.add((i+1).toString())
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.spinner_list, childList)
        binding.spnChild.adapter = adapter

        binding.spnChild.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p2!= 0) {
                    selectedNoofChild = binding.spnChild.selectedItem.toString().toInt()
                    binding.linearPolicyview.visibility =View.VISIBLE
                    generateChildAgeView(p2)
                }else{
                    selectChildAge.clear()
                    selectedNoofChild = 0
                    binding.linearPolicyview.visibility =View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun generateChildAgeView(count:Int) {
        binding.parentChildAgeView.removeAllViews()
        selectChildAge.clear()

        for (i in 0 until count){
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.row_age, null)
            val ageBubble = view.findViewById<LinearLayout>(R.id.ageView)
            val tvChildHeader = view.findViewById<TextView>(R.id.tvChildHeader)
            tvChildHeader.text = "Age of Child "+(i+1)

            val rbGroup = RadioGroup(context)
            rbGroup.orientation = RadioGroup.HORIZONTAL
            rbGroup.removeAllViews()
            val rb = arrayOfNulls<RadioButton>(11)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            selectChildAge.add(0)
            layoutParams.setMargins(0, 0, 20, 0)

            for(j in 0 until 11) {
                rb[j]  =  RadioButton(context)
                rb[j]!!.height = 100
                rb[j]!!.width = 100
                rb[j]!!.elevation = 10F
                rb[j]!!.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.black))
                rb[j]!!.buttonDrawable = null
                rb[j]!!.gravity = Gravity.CENTER
                rb[j]!!.text = (j + 1).toString()
                rb[j]!!.background = this.resources.getDrawable(R.drawable.radio_age_selector)
                rb[j]!!.id = j + 1
                rbGroup.addView(rb[j],layoutParams)

                rb[j]?.setOnClickListener {
                    selectChildAge[i] = rb[j]?.text.toString().toInt()
                }
            }
            ageBubble.addView(rbGroup)
            binding.parentChildAgeView.addView(view)
        }
    }
}