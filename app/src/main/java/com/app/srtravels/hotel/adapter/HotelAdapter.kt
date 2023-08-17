package com.app.srtravels.hotel.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.srtravels.R
import com.app.srtravels.databinding.RowHotelSelectionBinding
import com.app.srtravels.hotel.model.Hotel
import com.app.srtravels.hotel.model.Room

class HotelAdapter(private val context: Context, private var selectedHotelID: Int, private val interaction: Interaction) :
    RecyclerView.Adapter<HotelAdapter.NavigationOptionViewHolder>(),HotelRoomAdapter.Interaction {

    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    var roomForHotel: MutableList<Int> = mutableListOf<Int>()



    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hotel>() {

        override fun areItemsTheSame(
            oldItem: Hotel,
            newItem: Hotel
        ): Boolean {
            return oldItem.hotelId == newItem.hotelId
        }

        override fun areContentsTheSame(
            oldItem: Hotel,
            newItem: Hotel
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onItemHotelSelected(position: Int, item: Hotel)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowHotelSelectionBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowHotelSelectionBinding>(LayoutInflater.from(parent.context),
                R.layout.row_hotel_selection, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Hotel>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        var item = differ.currentList[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.mRadioButton.setOnCheckedChangeListener(null)

        if(selectedHotelID.equals(item.hotelId)) {
            selectedItemPos = position
            lastItemSelectedPos = position
            //get the selected parent child ids, so that user can not select other parent child item
            roomForHotel.clear()
            for(i in 0 until item.rooms.size){
                roomForHotel.add(item.rooms[i].roomId)
            }
        }

        if(position == selectedItemPos) {
            holder.itemDataBindingUtil.mRadioButton.isChecked = true
            holder.itemDataBindingUtil.container.setBackgroundResource(R.color.colorAccent)
        } else {
            holder.itemDataBindingUtil.mRadioButton.isChecked = false
            holder.itemDataBindingUtil.container.setBackgroundResource(R.color.white)
        }

        holder.itemDataBindingUtil.mRadioButton.setOnCheckedChangeListener { _, _ ->
            selectedItemPos = position
            selectedHotelID = item.hotelId
            //get the selected parent child ids, so that user can not select other parent child item
            roomForHotel.clear()
            for(i in 0 until item.rooms.size){
                roomForHotel.add(item.rooms[i].roomId)
            }

            lastItemSelectedPos = if(lastItemSelectedPos == -1)
                selectedItemPos
            else {
                notifyItemChanged(lastItemSelectedPos)
                selectedItemPos
            }
            notifyItemChanged(selectedItemPos)
        }

        holder.itemDataBindingUtil.hotelBanner.load(item?.hotelImages?.get(0)) {
            scale(Scale.FILL)
        }

        holder.itemDataBindingUtil.roomListView.apply {
            Toast.makeText(context,item.rooms.toString(),Toast.LENGTH_SHORT).show()
            Log.e("ROOMS",item.rooms.toString())

            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.roomListView.context, LinearLayoutManager.VERTICAL, false)
            adapter = HotelRoomAdapter(context,item.rooms, this@HotelAdapter, roomForHotel)
            holder.itemDataBindingUtil.roomListView.setRecycledViewPool(viewPool)
        }
    }

    class ViewHolder(
        val itemDataBindingUtil: RowHotelSelectionBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onRoomSelected(position: Int, item: Room) {
        Toast.makeText(context,item.roomName,Toast.LENGTH_SHORT).show()
    }
}