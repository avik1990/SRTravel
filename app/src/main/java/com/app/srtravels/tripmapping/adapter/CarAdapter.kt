package com.app.srtravels.tripmapping.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowCarsBinding
import com.app.srtravels.databinding.RowRoomBinding
import com.app.srtravels.tripmapping.model.Car
import com.app.srtravels.tripmapping.model.Room

class CarAdapter(private val context: Context, private val children: List<Car>, private val interaction: Interaction) :
    RecyclerView.Adapter<CarAdapter.NavigationOptionViewHolder>() {

    var currentItemSelected: Int = 0
    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Car>() {

        override fun areItemsTheSame(
            oldItem: Car,
            newItem: Car
        ): Boolean {
            return oldItem.carOwnerName == newItem.carOwnerName
        }

        override fun areContentsTheSame(
            oldItem: Car,
            newItem: Car
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onCarSelected(position: Int, item: Car)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowCarsBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowCarsBinding>(LayoutInflater.from(parent.context),
            R.layout.row_cars, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    fun submitList(list: List<Car>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = children[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
    }
}
