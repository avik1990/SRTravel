package com.app.srtravels.tripmapping.module.hotel.adapter

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
import com.app.srtravels.tripmapping.module.hotel.model1.Hotel
import com.app.srtravels.tripmapping.module.hotel.model1.HotelRoom
import com.app.srtravels.util.IMAGE_PATH_URL

class HotelAdapter(private val context: Context, private var selectedHotelID: String, private val interaction: Interaction) :
    RecyclerView.Adapter<HotelAdapter.NavigationOptionViewHolder>(), HotelRoomAdapter.Interaction {

    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    var roomForHotel: MutableList<String> = mutableListOf<String>()



    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hotel>() {

        override fun areItemsTheSame(
            oldItem: Hotel,
            newItem: Hotel
        ): Boolean {
            return oldItem.HotelGuid == newItem.HotelGuid
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
        fun onItemHotelRoomSelected(position: Int, item: HotelRoom)
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
        holder.itemDataBindingUtil.mSelectHotel.setOnCheckedChangeListener(null)

        Log.e("selectedHotelID",selectedHotelID)
        Log.e("selectedHotelID--",item.HotelGuid)

        if(selectedHotelID.equals(item.HotelGuid)) {
            selectedItemPos = position
            lastItemSelectedPos = position
            //get the selected parent child ids, so that user can not select other parent child item
            roomForHotel.clear()
            for(i in 0 until item.HotelRooms.size){
                roomForHotel.add(item.HotelRooms[i].HotelRoomGuid)
            }
        }

        if(position == selectedItemPos) {
            holder.itemDataBindingUtil.mSelectHotel.isChecked = true
            holder.itemDataBindingUtil.container.setBackgroundResource(R.drawable.rounded_selected_blue)
            holder.itemDataBindingUtil.mSelectHotel.text = "Selected Hotel"
        } else {
            holder.itemDataBindingUtil.mSelectHotel.isChecked = false
            holder.itemDataBindingUtil.container.setBackgroundResource(R.color.white)
            holder.itemDataBindingUtil.mSelectHotel.text = "Select Hotel"
        }

        holder.itemDataBindingUtil.mSelectHotel.setOnCheckedChangeListener { _, _ ->
            selectedItemPos = position
            selectedHotelID = item.HotelGuid
            //get the selected parent child ids, so that user can not select other parent child item
            roomForHotel.clear()
            for(i in 0 until item.HotelRooms.size){
                roomForHotel.add(item.HotelRooms[i].HotelRoomGuid)
            }
            lastItemSelectedPos = if(lastItemSelectedPos == -1)
                selectedItemPos
            else {
                notifyItemChanged(lastItemSelectedPos)
                selectedItemPos
            }
            notifyItemChanged(selectedItemPos)
        }

        holder.itemDataBindingUtil.hotelBanner.load(IMAGE_PATH_URL + item?.HotelImages?.get(0)?.HotelImages) {
            scale(Scale.FILL)
        }

        holder.itemDataBindingUtil.roomListView.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.roomListView.context, LinearLayoutManager.VERTICAL, false)
            adapter = HotelRoomAdapter(context,item.HotelRooms, this@HotelAdapter, roomForHotel)
        }
    }

    class ViewHolder(
        val itemDataBindingUtil: RowHotelSelectionBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onRoomSelected(position: Int, item: HotelRoom) {
        Toast.makeText(context,item.HotelRoomName,Toast.LENGTH_SHORT).show()
        interaction.onItemHotelRoomSelected(position,item)

    }
}