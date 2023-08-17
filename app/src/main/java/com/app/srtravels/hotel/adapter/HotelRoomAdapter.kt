package com.app.srtravels.hotel.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowRoomSelectionBinding
import com.app.srtravels.hotel.model.Room
import com.app.srtravels.tripmapping.model.HotelRoom

class HotelRoomAdapter(private val context: Context, private val children: List<Room>,
                       private val interaction: Interaction,private val roomIds: List<Int>) :
    RecyclerView.Adapter<HotelRoomAdapter.NavigationOptionViewHolder>() {

    var selectedItemPos = -1
    var currentItemSelected: Int = 0
    var lastItemSelectedPos = -1

    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Room>() {

        override fun areItemsTheSame(
            oldItem: Room,
            newItem: Room
        ): Boolean {
            return oldItem.roomName == newItem.roomName
        }

        override fun areContentsTheSame(
            oldItem: Room,
            newItem: Room
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onRoomSelected(position: Int, item: Room)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowRoomSelectionBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowRoomSelectionBinding>(LayoutInflater.from(parent.context),
            R.layout.row_room_selection, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    fun submitList(list: List<Room>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = children[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position

        Log.e("RoomIds===============",roomIds.toString())
        if(roomIds.contains(item.roomId)){
            holder.itemDataBindingUtil.mSelectRoom.visibility = View.VISIBLE
        }else{
            holder.itemDataBindingUtil.mSelectRoom.visibility = View.GONE
        }

        val list: List<String> = listOf(*item.roomFacility.split(",").toTypedArray())
        if(list.isNotEmpty()){
            val buttonLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            holder.itemDataBindingUtil.vContainer.removeAllViews()
            buttonLayoutParams.setMargins(5, 5, 5, 5)
            for (i in list.indices) {
                val tv = TextView(context)
                tv.text = list[i]
                tv.height = 80
                tv.textSize = 8.0f
                tv.gravity = Gravity.CENTER
                tv.setTextColor(Color.parseColor("#000000"))
                tv.background = context.resources.getDrawable(R.drawable.rounded_corner_yellow)
                tv.id = i + 1
                tv.layoutParams = buttonLayoutParams
                tv.tag = i
                tv.setPadding(10, 10, 10, 10)
                holder.itemDataBindingUtil.vContainer.addView(tv)
            }
        }

        holder.itemDataBindingUtil.mSelectRoom.setOnCheckedChangeListener(null)

        if(position == selectedItemPos) {
            holder.itemDataBindingUtil.mSelectRoom.isChecked = true
            holder.itemDataBindingUtil.container.setBackgroundResource(R.color.colorAccent)
        }
        else {
            holder.itemDataBindingUtil.mSelectRoom.isChecked = false
            holder.itemDataBindingUtil.container.setBackgroundResource(R.color.gray)
        }

        holder.itemDataBindingUtil.mSelectRoom.setOnCheckedChangeListener { _, _ ->
            selectedItemPos = position
            lastItemSelectedPos = if(lastItemSelectedPos == -1)
                selectedItemPos
            else {
                notifyItemChanged(lastItemSelectedPos)
                selectedItemPos
            }
            notifyItemChanged(selectedItemPos)
        }

    }
}
