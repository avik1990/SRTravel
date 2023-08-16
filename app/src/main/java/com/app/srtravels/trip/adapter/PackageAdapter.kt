package com.app.srtravels.trip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.srtravels.R
import com.app.srtravels.databinding.RowTripsPackageHorizontalBinding
import com.app.srtravels.home.modules.trip.model.Trip
import com.app.srtravels.trip.model.PackageDetail

class PackageAdapter(private val children: List<PackageDetail>, private val interaction: Interaction) :
    RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageAdapter.ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowTripsPackageHorizontalBinding>(LayoutInflater.from(parent.context), R.layout.row_trips_package_horizontal, parent, false)
        return ViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.itemDataBindingUtil.navigationItem = child
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.movieBanner.load(child.packageThumbnail) {
            scale(Scale.FILL)
        }
    }

    interface Interaction {
        fun onChildItemSelected(position: Int, item: PackageDetail)
    }

    class ViewHolder(
        val itemDataBindingUtil: RowTripsPackageHorizontalBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)
}
