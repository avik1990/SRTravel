package com.app.srtravels.home.modules.trip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowTripHeaderBinding
import com.app.srtravels.home.modules.trip.model.Trip
import com.app.srtravels.home.modules.trip.model.Tripcategory

class TripCategoryAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<TripCategoryAdapter.NavigationOptionViewHolder>(), TripAdapter.Interaction {

    var currentItemSelected: Int = 0
    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tripcategory>() {

        override fun areItemsTheSame(
            oldItem: Tripcategory,
            newItem: Tripcategory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Tripcategory,
            newItem: Tripcategory
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onItemSelectedHorizontal(position: Int, item: Trip)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowTripHeaderBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowTripHeaderBinding>(LayoutInflater.from(parent.context), R.layout.row_trip_header, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Tripcategory>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)

        holder.itemDataBindingUtil.listSubCatItem.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.listSubCatItem.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TripAdapter(item.trips, this@TripCategoryAdapter)
            holder.itemDataBindingUtil.listSubCatItem.setRecycledViewPool(viewPool)
        }
    }

    override fun onChildItemSelected(position: Int, item: Trip) {
        interaction.onItemSelectedHorizontal(position, item)
    }
}
