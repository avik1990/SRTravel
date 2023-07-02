package com.app.srtravels.searchpackage

import android.annotation.SuppressLint
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.srtravels.R
import com.app.srtravels.databinding.FragmentSearchBinding
import com.app.srtravels.util.MAX_PERSON_PER_TRIP
import com.google.android.material.bottomsheet.BottomSheetDialog

class SearchFragment : Fragment() {

    var adultList: MutableList<String> = ArrayList()
    var childList: MutableList<String> = ArrayList()
    var selectChildAge: MutableList<Int> = ArrayList()

    var noOfAdults = 0
    var noOfChild = 0

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

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



        binding.btnSearch.setOnClickListener {
            Toast.makeText(context,selectChildAge.toString(),Toast.LENGTH_SHORT).show()
        }

        val adapter: ArrayAdapter<String> =
        ArrayAdapter<String>(requireContext(), R.layout.spinner_list, adultList)
        binding.spnAdult.adapter = adapter
        binding.spnAdult.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position>0){
                     noOfAdults = binding.spnAdult.selectedItem.toString().toInt()
                     noOfChild = MAX_PERSON_PER_TRIP - noOfAdults.toInt()

                    if(noOfAdults >= 14) {
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
        for (i in 0 until noOfChild) {
            childList.add((i+1).toString())
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.spinner_list, childList)
        binding.spnChild.adapter = adapter

        binding.spnChild.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if(p2!= 0) {
                    binding.linearPolicyview.visibility =View.VISIBLE
                    generateChildAgeView(p2)
                }else{
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
                    Toast.makeText(context,rb[j]?.text,Toast.LENGTH_SHORT).show()
                    selectChildAge[j] = rb[j]?.text.toString().toInt()
                }
            }

            ageBubble.addView(rbGroup)
            binding.parentChildAgeView.addView(view)
        }
    }
}