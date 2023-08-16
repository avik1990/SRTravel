package com.app.srtravels.recentlyviewed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.srtravels.R

class RecentlyViewedFragment : Fragment() {

    companion object {
        fun newInstance() = RecentlyViewedFragment()
    }

    private lateinit var viewModel: RecentlyViewedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recently_viewed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecentlyViewedViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
