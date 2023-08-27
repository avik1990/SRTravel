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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.ChildAgeCounterBinding
import com.app.srtravels.tripmapping.addroom.model.ChildAgeLimit
import com.app.srtravels.util.MAX_CHILD_AGE_LIMIT

class ChildAgeCounterAdapter(private val context: Context, private val children: MutableList<Int>,
                             private val interaction: Interaction) :
    RecyclerView.Adapter<ChildAgeCounterAdapter.ViewHolder>() {

    var selectedItemPos = -1
    var currentItemSelected: Int = 0
    var lastItemSelectedPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<ChildAgeCounterBinding>(LayoutInflater.from(parent.context),
            R.layout.child_age_counter, parent, false)

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
        holder.itemDataBindingUtil.tvAge.text = child.toString()
        holder.itemDataBindingUtil.tvAge.setOnCheckedChangeListener(null)

        if(position == selectedItemPos) {
            //interaction.onChildAgeSelection(position, child)
            holder.itemDataBindingUtil.tvAge.isChecked = true
            holder.itemDataBindingUtil.tvAge.setBackgroundResource(R.drawable.rounded_selected_blue)
        } else {
            //interaction.onChildAgeSelection(position,child)
            holder.itemDataBindingUtil.tvAge.isChecked = false
            holder.itemDataBindingUtil.tvAge.setBackgroundResource(R.drawable.rounded_grey)
        }

        holder.itemDataBindingUtil.tvAge.setOnCheckedChangeListener { _, _ ->
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

    interface Interaction {
        fun onChildAgeSelection(position: Int, values:Int)
    }

    class ViewHolder(
        val itemDataBindingUtil: ChildAgeCounterBinding,
        val interaction: Interaction
    ) : RecyclerView.ViewHolder(itemDataBindingUtil.root)

}
