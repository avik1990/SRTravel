package com.app.srtravels.home.module

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.app.srtravels.R
import com.app.srtravels.databinding.FragmentHomeBinding
import com.app.srtravels.databinding.FragmentSliderBinding
import com.app.srtravels.home.module.adapter.SliderAdapter
import com.app.srtravels.home.module.model.BannerModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask


class SliderFragment : Fragment(),SliderAdapter.Interaction {

    companion object {
        fun newInstance() = SliderFragment()
    }

    private lateinit var viewModel: SliderViewModel
    private lateinit var _binding: FragmentSliderBinding
    private lateinit var adapter: SliderAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSliderBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SliderViewModel::class.java]

        prepareSliderData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareSliderData() {
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = SliderAdapter(requireContext(), this)
        _binding.bannerSlider.adapter = adapter
        _binding.bannerSlider.layoutManager = linearLayoutManager
        //PagerSnapHelper().attachToRecyclerView(_binding.bannerSlider)
        adapter.submitList(viewModel.getSLiderImages())
        adapter.notifyDataSetChanged()
        LinearSnapHelper().attachToRecyclerView(_binding.bannerSlider)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.itemCount -1)){
                    linearLayoutManager.smoothScrollToPosition(_binding.bannerSlider,RecyclerView.State(),
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
                }else if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.itemCount -1)){
                    linearLayoutManager.smoothScrollToPosition(_binding.bannerSlider,RecyclerView.State(),
                        0)
                }
            }
        }, 0,5000)

    }

    override fun onItemSelected(position: Int, item: BannerModel) {
        TODO("Not yet implemented")
    }

}