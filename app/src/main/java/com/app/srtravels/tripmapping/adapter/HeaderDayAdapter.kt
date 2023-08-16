package com.app.srtravels.tripmapping.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowHeaderDayBinding
import com.app.srtravels.tripmapping.model.Day
import com.app.srtravels.tripmapping.model.Hotel
import com.app.srtravels.tripmapping.model.Place
import com.app.srtravels.tripmapping.model.Route

class HeaderDayAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<HeaderDayAdapter.NavigationOptionViewHolder>(),
    DayWiseDetailsAdapter.Interaction,
    PlaceAdapter.Interaction,
    RouteAdapter.Interaction {

    var currentItemSelected: Int = 0
    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Day>() {

        override fun areItemsTheSame(
            oldItem: Day,
            newItem: Day
        ): Boolean {
            return oldItem.dayName == newItem.dayName
        }

        override fun areContentsTheSame(
            oldItem: Day,
            newItem: Day
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onHotelChangeItem(position: Int, item: Hotel)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowHeaderDayBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowHeaderDayBinding>(LayoutInflater.from(parent.context),
            R.layout.row_header_day, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Day>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.setIsRecyclable(false)
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)

        holder.itemDataBindingUtil.listSubCatItem.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.listSubCatItem.context, LinearLayoutManager.VERTICAL, false)
            adapter = DayWiseDetailsAdapter(context,item.hotels, this@HeaderDayAdapter)
            holder.itemDataBindingUtil.listSubCatItem.setRecycledViewPool(viewPool)
        }

        holder.itemDataBindingUtil.placeListView.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.placeListView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = PlaceAdapter(context,item.places, this@HeaderDayAdapter)
            holder.itemDataBindingUtil.placeListView.setRecycledViewPool(viewPool)
        }

        holder.itemDataBindingUtil.carListView.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.carListView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RouteAdapter(context,item.routes, this@HeaderDayAdapter)
            holder.itemDataBindingUtil.carListView.setRecycledViewPool(viewPool)
        }
    }

    override fun onPlaceSelected(position: Int, item: Place) {
        Toast.makeText(context,item.placeName,Toast.LENGTH_LONG).show()
    }

    override fun onRouteSelected(position: Int, item: Route) {
        Toast.makeText(context,item.routeName,Toast.LENGTH_LONG).show()
    }

    override fun onHotelSelected(position: Int, item: Hotel) {
        interaction.onHotelChangeItem(position,item)
    }
}
