package com.app.srtravels.tripmapping.addroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.ChildAgeCounterBinding
import com.app.srtravels.tripmapping.addroom.model.ChildAgeLimit

class ChildAgeCounterAdapter(
    private val context: Context, private val children: MutableList<Int>,
    private val interaction: ChildAgeCounterInteraction,
    private val children1: MutableList<ChildAgeLimit>,
    private val roomAdapterPosition: Int,
    private val childAgeAdapterPosition: Int
) :
    RecyclerView.Adapter<ChildAgeCounterAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<ChildAgeCounterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.child_age_counter, parent, false
        )

        return ViewHolder(itemDatabinding, interaction)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.itemDataBindingUtil.navigationItem = child
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.tvAge.text = child.toString()
        holder.itemDataBindingUtil.tvAge.setOnCheckedChangeListener(null)

        try {
            if (children1[childAgeAdapterPosition].selectedChildAge == child) {
                holder.itemDataBindingUtil.tvAge.isChecked = true
                holder.itemDataBindingUtil.tvAge.setBackgroundResource(R.drawable.rounded_selected_blue)
            } else {
                holder.itemDataBindingUtil.tvAge.isChecked = false
                holder.itemDataBindingUtil.tvAge.setBackgroundResource(R.drawable.rounded_grey)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.itemDataBindingUtil.tvAge.setOnClickListener {
            interaction.onChildAgeCounterAdapterSelection(
                roomAdapterPosition,
                childAgeAdapterPosition,
                children[position]
            )
        }
    }

    interface ChildAgeCounterInteraction {
        fun onChildAgeCounterAdapterSelection(parentPosition: Int, childPosition: Int, values: Int)
    }

    class ViewHolder(
        val itemDataBindingUtil: ChildAgeCounterBinding,
        val interaction: ChildAgeCounterInteraction
    ) : RecyclerView.ViewHolder(itemDataBindingUtil.root)

}
