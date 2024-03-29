package com.app.srtravels.tripmapping.addroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.ItemAddRoomBinding
import com.app.srtravels.tripmapping.addroom.model.RoomInputModel

class AddRoomAdapter(
    private val context: Context,
    private val interaction: AddRoomAdapterInteraction,
    private val childAgeCounterInteraction: ChildAgeCounterAdapter.ChildAgeCounterInteraction,
    private val childAgeAdapterInteraction: ChildAgeAdapter.ChildAgeAdapterInteraction
) : RecyclerView.Adapter<AddRoomAdapter.NavigationOptionViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RoomInputModel>() {
        override fun areItemsTheSame(
            oldItem: RoomInputModel,
            newItem: RoomInputModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RoomInputModel,
            newItem: RoomInputModel
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    interface AddRoomAdapterInteraction {
        fun onCustomRoomSelection(
            position: Int, item: RoomInputModel,
            flag: String
        )
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: ItemAddRoomBinding,
        val interaction: AddRoomAdapterInteraction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<ItemAddRoomBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_add_room, parent, false
        )
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<RoomInputModel>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        var item = differ.currentList[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.roomsCount.text = "Room " + (position + 1)
        item.roomName = "Room " + (position + 1)

        item.childAgeRoomWise.let {
            holder.itemDataBindingUtil.childList.apply {
                layoutManager = LinearLayoutManager(
                    holder.itemDataBindingUtil.childList.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )

                adapter = it?.let { it1 ->
                    ChildAgeAdapter(
                        context,
                        it1,
                        interaction,
                        childAgeCounterInteraction,
                        childAgeAdapterInteraction,
                        position,
                        differ.currentList
                    )
                }
            }
        }

        if (position == 0) {
            holder.itemDataBindingUtil.tvRemove.visibility = View.GONE
        } else {
            holder.itemDataBindingUtil.tvRemove.visibility = View.VISIBLE
        }

        holder.itemDataBindingUtil.tvRemove.setOnClickListener {
            interaction.onCustomRoomSelection(position, differ.currentList[position], "delete")
        }

        holder.itemDataBindingUtil.incrementAdult.setOnClickListener {
            interaction.onCustomRoomSelection(
                position,
                differ.currentList[position],
                "addNoOfAdults"
            )
        }

        holder.itemDataBindingUtil.decrementAdult.setOnClickListener {
            interaction.onCustomRoomSelection(
                position,
                differ.currentList[position],
                "removeNoOfAdults"
            )
        }

        holder.itemDataBindingUtil.incrementChild.setOnClickListener {
            interaction.onCustomRoomSelection(
                position,
                differ.currentList[position],
                "addNoOfChild"
            )
        }

        holder.itemDataBindingUtil.decrementChild.setOnClickListener {
            interaction.onCustomRoomSelection(
                position,
                differ.currentList[position],
                "removeNoOfChild"
            )
        }
    }
}
