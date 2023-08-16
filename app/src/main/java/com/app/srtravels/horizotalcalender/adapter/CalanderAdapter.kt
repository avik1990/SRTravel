package com.app.srtravels.horizotalcalender.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowCalendersBinding
import com.app.srtravels.horizotalcalender.model.Calenders

class CalanderAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<CalanderAdapter.NavigationOptionViewHolder>() {

    var currentItemSelected: Int = 0
    private lateinit var adapter: CalanderAdapter

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Calenders>() {

        override fun areItemsTheSame(
            oldItem: Calenders,
            newItem: Calenders
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Calenders,
            newItem: Calenders
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onItemSelected(position: Int, item: Calenders)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowCalendersBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowCalendersBinding>(LayoutInflater.from(parent.context), R.layout.row_calenders, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Calenders>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)

        if (currentItemSelected == position) {
            holder.itemDataBindingUtil.background.setBackgroundResource(R.color.babyBlue)
            holder.itemDataBindingUtil.monthView.setBackgroundResource(R.color.babyBlue)
            holder.itemDataBindingUtil.dayView.setBackgroundResource(R.color.babyBlue)
        } else {
            holder.itemDataBindingUtil.background.setBackgroundResource(R.color.white)
            holder.itemDataBindingUtil.monthView.setBackgroundResource(R.color.white)
            holder.itemDataBindingUtil.dayView.setBackgroundResource(R.color.white)
        }

    }
}
