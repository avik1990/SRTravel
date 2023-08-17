package com.app.srtravels.tripmapping.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowPlaceBinding
import com.app.srtravels.tripmapping.model.Place


class PlaceAdapter(private val context: Context, private val children: List<Place>, private val interaction: Interaction) :
    RecyclerView.Adapter<PlaceAdapter.NavigationOptionViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Place>() {

        override fun areItemsTheSame(
            oldItem: Place,
            newItem: Place
        ): Boolean {
            return oldItem.PlaceGuid == newItem.PlaceGuid
        }

        override fun areContentsTheSame(
            oldItem: Place,
            newItem: Place
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onPlaceSelected(position: Int, item: Place)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowPlaceBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowPlaceBinding>(LayoutInflater.from(parent.context),
            R.layout.row_place, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    fun submitList(list: List<Place>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = children[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
    }
}
