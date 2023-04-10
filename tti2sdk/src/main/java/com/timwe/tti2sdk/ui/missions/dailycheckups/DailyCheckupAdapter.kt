package com.timwe.tti2sdk.ui.missions.dailycheckups

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Mission

class DailyCheckupAdapter(
    private val context: Context,
    private val data: List<Mission> = emptyList(),
    private val mGlide: RequestManager,
    private val itemListener: (Mission) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_daily_checkup, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var flagText1: TextView = itemView.findViewById(R.id.txtFlag1)
        var flagContainerColor: ConstraintLayout = itemView.findViewById(R.id.containerFlags)

        var title: TextView = itemView.findViewById(R.id.txtTitle)
        var subtitle: TextView = itemView.findViewById(R.id.txtSubtitle)
        var distance: TextView = itemView.findViewById(R.id.txtDistance)
        var distanceUnit: TextView = itemView.findViewById(R.id.txtDistanceUnit)

        var iconLeft: ImageView = itemView.findViewById(R.id.iconLeft)
        var iconRight: ImageView = itemView.findViewById(R.id.iconRight)

        init{
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        defaultVH.flagText1.text = item.flagText
        defaultVH.flagContainerColor.setBackgroundColor(context.getColor(R.color.red_default))

        defaultVH.distance.text = item.distance.toString()
        defaultVH.distanceUnit.text = item.distanceUnit.toString()

        defaultVH.title.text = item.title
        defaultVH.subtitle.text = item.subtitle

        //defaultVH.iconLeft.setImageResource(context.resources = R.drawable.ic)

        //IconRight
        when(item.type){
            "hasActions" -> { defaultVH.iconRight.setImageResource(R.drawable.ic_forward) }
            "error" -> { defaultVH.iconRight.setImageResource(R.drawable.icon_error) }
            "success" -> { defaultVH.iconRight.setImageResource(R.drawable.icon_success)}
            "warning" -> { defaultVH.iconRight.setImageResource(R.drawable.ic_warning) }
        }

        //IconLeft
    }
}