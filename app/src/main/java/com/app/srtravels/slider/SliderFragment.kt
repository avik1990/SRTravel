package com.app.srtravels.slider

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.databinding.FragmentSliderBinding
import com.app.srtravels.slider.adapter.SliderAdapter
import com.app.srtravels.slider.model.BannerModel
import java.util.Timer
import java.util.TimerTask

class SliderFragment : Fragment(), SliderAdapter.Interaction {

    companion object {
        fun newInstance() = SliderFragment()
    }

    private lateinit var viewModel: SliderViewModel
    private lateinit var _binding: FragmentSliderBinding
    private lateinit var adapter: SliderAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        adapter.submitList(viewModel.getSLiderImages())
        adapter.notifyDataSetChanged()
        PagerSnapHelper().attachToRecyclerView(_binding.bannerSlider)

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.itemCount - 1)) {
                        linearLayoutManager.smoothScrollToPosition(
                            _binding.bannerSlider, RecyclerView.State(),
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1
                        )
                    } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.itemCount - 1)) {
                        linearLayoutManager.smoothScrollToPosition(
                            _binding.bannerSlider, RecyclerView.State(),
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() - 1
                        )
                    }
                }
            },
            0, 5000
        )
    }

    override fun onItemSelected(position: Int, item: BannerModel) {
        TODO("Not yet implemented")
    }


}
