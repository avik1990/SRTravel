package com.app.srtravels.slider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.app.srtravels.R
import com.app.srtravels.databinding.RowAllViewpagerBinding
import com.app.srtravels.slider.model.BannerModel

class SliderAdapter(private val context: Context, private val interaction: Interaction) :
    RecyclerView.Adapter<SliderAdapter.NavigationOptionViewHolder>() {

    var currentItemSelected: Int = 0

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BannerModel>() {

        override fun areItemsTheSame(
            oldItem: BannerModel,
            newItem: BannerModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BannerModel,
            newItem: BannerModel
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * Interface for any kind of listener event in recyclerView
     * */
    interface Interaction {
        fun onItemSelected(position: Int, item: BannerModel)
    }

    class NavigationOptionViewHolder(
        val itemDataBindingUtil: RowAllViewpagerBinding,
        val interaction: Interaction
    ) :
        RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationOptionViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<RowAllViewpagerBinding>(LayoutInflater.from(parent.context), R.layout.row_all_viewpager, parent, false)
        return NavigationOptionViewHolder(
            itemDatabinding,
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<BannerModel>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: NavigationOptionViewHolder, position: Int) {
        val item = differ.currentList[position]
        /**
         * Assigning the variables in to data binding variables for showing the data
         * */
        holder.itemDataBindingUtil.navigationItem = item
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        holder.itemDataBindingUtil.checked = (currentItemSelected == position)
        // val url = "https://www.themoviedb.org/t/p/w500" + item?.poster_path
        // Log.e("poster_path", url)
       /* Glide.with(context)
            .load(url)
            .into(holder.itemDataBindingUtil.imgBanner!!)*/
        holder.itemDataBindingUtil.imgBanner.load(item?.imgURl) {
            // crossfade(750)
            // placeholder(errorPlaceHolder)
            // transformations(CircleCropTransformation())
            // error(errorPlaceHolder)
            scale(Scale.FILL)
        }
    }
}
