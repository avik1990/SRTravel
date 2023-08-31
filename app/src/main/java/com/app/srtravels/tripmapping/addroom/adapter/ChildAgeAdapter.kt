package com.app.srtravels.tripmapping.addroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.ChildAgeBinding
import com.app.srtravels.tripmapping.addroom.model.ChildAgeLimit
import com.app.srtravels.tripmapping.addroom.model.RoomInputModel

class ChildAgeAdapter(
    private val context: Context, private val children: MutableList<ChildAgeLimit>,
    private val addroomInteraction: AddRoomAdapter.AddRoomAdapterInteraction,
    private val childAgeCounterInteraction: ChildAgeCounterAdapter.ChildAgeCounterInteraction,
    private val childAgeAdapterInteraction: ChildAgeAdapterInteraction,
    private val roomPosition: Int,
    roomInputModel: MutableList<RoomInputModel>
) :
    RecyclerView.Adapter<ChildAgeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<ChildAgeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.child_age, parent, false
        )
        return ViewHolder(
            itemDatabinding,
            childAgeAdapterInteraction
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = children[position]
        holder.itemDataBindingUtil.navigationItem = child
        holder.itemDataBindingUtil.clickEvent = childAgeAdapterInteraction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.tvChildHeader.visibility = View.GONE

        child.childAgeList.let {
            holder.itemDataBindingUtil.childAgeList.apply {
                layoutManager = LinearLayoutManager(
                    holder.itemDataBindingUtil.childAgeList.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = children[position].childAgeList?.let { it1 ->
                    holder.itemDataBindingUtil.tvChildHeader.visibility = View.VISIBLE
                    holder.itemDataBindingUtil.tvChildHeader.text = "Age of Child " + (position + 1)
                    ChildAgeCounterAdapter(
                        context, it1, childAgeCounterInteraction, children,
                        roomPosition,
                        position
                    )
                }
            }
        }
    }

    interface ChildAgeAdapterInteraction {
        fun onChildAgeList(position: Int, item: Int)
    }

    class ViewHolder(
        val itemDataBindingUtil: ChildAgeBinding,
        val interaction: ChildAgeAdapterInteraction
    ) : RecyclerView.ViewHolder(itemDataBindingUtil.root)

}
