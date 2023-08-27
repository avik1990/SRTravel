package com.app.srtravels.tripmapping.addroom.adapter

import android.content.Context
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
import androidx.recyclerview.widget.RecyclerView
import com.app.srtravels.R
import com.app.srtravels.databinding.ChildAgeBinding
import com.app.srtravels.tripmapping.addroom.model.ChildAgeLimit

class ChildAgeAdapter(
    private val context: Context, private val children: MutableList<ChildAgeLimit>,private val counter: Int,
    private val interaction: Interaction
) :
    RecyclerView.Adapter<ChildAgeAdapter.ViewHolder>(), ChildAgeCounterAdapter.Interaction {

    var adapterPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemDatabinding = DataBindingUtil.inflate<ChildAgeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.child_age, parent, false
        )
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
        adapterPosition = position
       // holder.setIsRecyclable(false)
        holder.itemDataBindingUtil.navigationItem = child
        holder.itemDataBindingUtil.clickEvent = interaction
        holder.itemDataBindingUtil.position = position
        //holder.itemDataBindingUtil.tvChildHeader.visibility = View.GONE
        holder.itemDataBindingUtil.tvChildHeader.text = "Age of Child " + (position + 1)

        generateChildAgeView(holder.itemDataBindingUtil.ageView,counter)

        /*child.childAgeList.let {
            holder.itemDataBindingUtil.childAgeList.apply {
                layoutManager = LinearLayoutManager(
                    holder.itemDataBindingUtil.childAgeList.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = children[position].childAgeList?.let { it1 ->
                    holder.itemDataBindingUtil.tvChildHeader.visibility = View.VISIBLE
                    holder.itemDataBindingUtil.tvChildHeader.text = "Age of Child " + (position + 1)
                    ChildAgeCounterAdapter(context, it1, this@ChildAgeAdapter)
                }
            }
        }*/
    }

    interface Interaction {
        fun onChildAgeList(position: Int, item: Int)
    }

    class ViewHolder(
        val itemDataBindingUtil: ChildAgeBinding,
        val interaction: Interaction
    ) : RecyclerView.ViewHolder(itemDataBindingUtil.root)

    override fun onChildAgeSelection(position: Int, values: Int) {
        Toast.makeText(context, values.toString(), Toast.LENGTH_SHORT).show()
        //interaction.onChildAgeList(adapterPosition, values)
        //children[position].selectedChildAgeList!!.add(values)
    }

    private fun generateChildAgeView(parentLayout:LinearLayout,count:Int) {
        //parentLayout.removeAllViews()
        //selectChildAge.clear()

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
            //selectChildAge.add(0)
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

                rb[j]?.setOnClickListener {
                   // selectChildAge[i] = rb[j]?.text.toString().toInt()
                }
            }
            ageBubble.addView(rbGroup)
            parentLayout.addView(view)
        }
    }


}
