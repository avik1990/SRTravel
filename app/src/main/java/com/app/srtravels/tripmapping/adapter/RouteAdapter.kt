package com.app.srtravels.tripmapping.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.RowRouteHeaderBinding
import com.app.srtravels.tripmapping.model.Car
import com.app.srtravels.tripmapping.model.Route


class RouteAdapter(private val context: Context, private val children: List<Route>,
                   private val interaction: Interaction) :
    RecyclerView.Adapter<RouteAdapter.ViewHolder>(),CarAdapter.Interaction {

    private val viewPool = RecyclerView.RecycledViewPool()

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Route>() {

        override fun areItemsTheSame(
            oldItem: Route,
            newItem: Route
        ): Boolean {
            return oldItem.RouteGuid == newItem.RouteGuid
        }

        override fun areContentsTheSame(
            oldItem: Route,
            newItem: Route
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onRouteSelected(position: Int, item: Route)
    }

    class ViewHolder(
        val itemDataBindingUtil: RowRouteHeaderBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowRouteHeaderBinding>(LayoutInflater.from(parent.context),
            R.layout.row_route_header, parent, false)
        return ViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return children.size
    }

    fun submitList(list: List<Route>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = children[position]
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position

        holder.itemDataBindingUtil.listCar.apply {
            layoutManager = LinearLayoutManager(holder.itemDataBindingUtil.listCar.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CarAdapter(context,item.Cars, this@RouteAdapter)
            holder.itemDataBindingUtil.listCar.setRecycledViewPool(viewPool)
        }
    }

    override fun onCarSelected(position: Int, item: Car) {
        Toast.makeText(context,item.CarName, Toast.LENGTH_LONG).show()
    }
}
