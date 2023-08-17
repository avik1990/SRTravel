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
import com.app.srtravels.databinding.RowRoomBinding
import com.app.srtravels.tripmapping.model.HotelRoom


class RoomAdapter(private val context: Context, private val children: List<HotelRoom>, private val interaction: Interaction) :
    RecyclerView.Adapter<RoomAdapter.NavigationOptionViewHolder>() {


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HotelRoom>() {

        override fun areItemsTheSame(
            oldItem: HotelRoom,
            newItem: HotelRoom
        ): Boolean {
            return oldItem.HotelRoomGuid == newItem.HotelRoomGuid
        }

        override fun areContentsTheSame(
            oldItem: HotelRoom,
            newItem: HotelRoom
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onRoomSelected(position: Int, item: HotelRoom)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowRoomBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowRoomBinding>(LayoutInflater.from(parent.context),
            R.layout.row_room, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )

    }

    override fun getItemCount(): Int {
        return children.size
    }

    fun submitList(list: List<HotelRoom>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = children[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        //holder.itemDataBindingUtil.checked = (currentItemSelected == position)

        /*holder.itemDataBindingUtil.listSubCatItem.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.listSubCatItem.context, LinearLayoutManager.VERTICAL, false)
            adapter = DayWiseDetailsAdapter(item.hotels, this@HotelRoomAdapter)
            holder.itemDataBindingUtil.listSubCatItem.setRecycledViewPool(viewPool)
        }*/
        val list: List<String> = listOf(*item.RoomFacilities.split(",").toTypedArray())
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
    }
}
