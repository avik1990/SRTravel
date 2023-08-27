package com.app.srtravels.tripmapping.addroom.adapter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.ItemAddRoomBinding
import com.app.srtravels.tripmapping.addroom.model.ChildAgeLimit
import com.app.srtravels.tripmapping.addroom.model.RoomInputModel
import java.lang.Exception

class AddRoomAdapter(context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<AddRoomAdapter.NavigationOptionViewHolder>(), ChildAgeAdapter.Interaction {

    var currentItemSelected: Int = 0
    var context: Context

    init {
        this.context = context
    }

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

    interface Interaction {
        fun onCustomRoomSelection(
            position: Int, item: RoomInputModel,
            flag: String
        )
        fun onAgeSelected(position: Int, childPosition:Int, age: Int)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: ItemAddRoomBinding,
        val interaction: Interaction
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

        Log.e("POSSSSS", position.toString() +""+ item)

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
            generateChildAgeView(position, holder.itemDataBindingUtil.parentChildAgeView,item.noOfChild,item)
        }

        holder.itemDataBindingUtil.decrementChild.setOnClickListener {
            interaction.onCustomRoomSelection(
                position,
                differ.currentList[position],
                "removeNoOfChild"
            )
            generateChildAgeView(position,holder.itemDataBindingUtil.parentChildAgeView,item.noOfChild,item)
        }
    }
    override fun onChildAgeList(position: Int, item: Int) {
        Toast.makeText(context, "Hello $item  - $position", Toast.LENGTH_SHORT).show()
        //differ.currentList[adapterPosition].childAgeLimit?.get(position)!!.selectedChildAgeList!!.add(item)
    }


    private fun generateChildAgeView(parentAdapterPosition:Int, parentLayout: LinearLayout, count:Int, item:RoomInputModel) {
        parentLayout.removeAllViews()
        ///selectChildAge.clear()
        for (i in 0 until count) {
            val view: View = LayoutInflater.from(context)
                .inflate(R.layout.row_age, null)
            val ageBubble = view.findViewById<LinearLayout>(R.id.ageView)

            val rbGroup = RadioGroup(context)
            rbGroup.orientation = RadioGroup.HORIZONTAL
            rbGroup.removeAllViews()
            val rb = arrayOfNulls<RadioButton>(11)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
           // selectChildAge.add(0)
            layoutParams.setMargins(0, 0, 20, 0)

            for(j in 0 until 11) {
                rb[j]  =  RadioButton(context)
                rb[j]!!.height = 100
                rb[j]!!.width = 100
                rb[j]!!.elevation = 10F
                rb[j]!!.setTextColor(ContextCompat.getColorStateList(context, R.color.black))
                rb[j]!!.buttonDrawable = null
                rb[j]!!.gravity = Gravity.CENTER
                rb[j]!!.text = (j + 1).toString()
                rb[j]!!.background = context.resources.getDrawable(R.drawable.radio_age_selector)
                rb[j]!!.id = j + 1
                rbGroup.addView(rb[j],layoutParams)

                rb[j]?.isChecked = item.childAgeLimit[i].selectedChildAgeList == j
                Log.e("AGEEEEEEEEEEEEEEEEEEE", item.childAgeLimit[i].selectedChildAgeList.toString())

                rb[j]?.setOnClickListener {
                    // selectChildAge[i] = rb[j]?.text.toString().toInt()
                    interaction.onAgeSelected(parentAdapterPosition,i,rb[j]?.text.toString().toInt())
                }
            }
           // item.selectedList = selectChildAge
            ageBubble.addView(rbGroup)
            parentLayout.addView(view)
        }
    }
}
