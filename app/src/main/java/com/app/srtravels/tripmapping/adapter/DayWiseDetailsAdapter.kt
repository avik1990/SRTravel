package com.app.srtravels.tripmapping.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.srtravels.R
import com.app.srtravels.databinding.RowHotelBinding
import com.app.srtravels.tripmapping.model.Hotel
import com.app.srtravels.tripmapping.model.Room

class DayWiseDetailsAdapter(private val context: Context,private val children: List<Hotel>, private val interaction: Interaction) :
    RecyclerView.Adapter<DayWiseDetailsAdapter.ViewHolder>(), RoomAdapter.Interaction {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowHotelBinding>(LayoutInflater.from(parent.context),
            R.layout.row_hotel, parent, false)
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

        holder.itemDataBindingUtil.movieBanner.load(child.hotelImages[0]) {
            scale(Scale.FILL)
        }

            holder.itemDataBindingUtil.roomListView.apply {
                layoutManager = LinearLayoutManager(
                    holder.itemDataBindingUtil.roomListView.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = RoomAdapter(context, child.rooms, this@DayWiseDetailsAdapter)
                holder.itemDataBindingUtil.roomListView.setRecycledViewPool(viewPool)
            }

    }

    interface Interaction {
        fun onHotelSelected(position: Int, item: Hotel)
    }

    class ViewHolder(
        val itemDataBindingUtil: RowHotelBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onRoomSelected(position: Int, item: Room) {
        Toast.makeText(context,item.roomName, Toast.LENGTH_LONG).show()
    }
}
