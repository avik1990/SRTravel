package com.app.srtravels.home.modules.trip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.srtravels.R
import com.app.srtravels.databinding.RowMovielistHorizontalBinding
import com.app.srtravels.home.modules.trip.model.Trip

class TripAdapter(private val children: List<Trip>, private val interaction: Interaction) :
    RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowMovielistHorizontalBinding>(LayoutInflater.from(parent.context), R.layout.row_movielist_horizontal, parent, false)
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

        //val url = "https://www.themoviedb.org/t/p/w500" + child.poster_path
        Log.e("ChildImage========",child.thumb)
        holder.itemDataBindingUtil.movieBanner.load(child.thumb) {
            scale(Scale.FILL)
        }
    }

    interface Interaction {
        fun onChildItemSelected(position: Int, item: Trip)
    }

    class ViewHolder(
        val itemDataBindingUtil: RowMovielistHorizontalBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)
}
