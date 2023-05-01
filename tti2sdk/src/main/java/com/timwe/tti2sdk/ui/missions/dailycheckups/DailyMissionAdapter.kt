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

class DailyMissionAdapter(
    private val context: Context,
    private val data: List<Mission> = emptyList(),
    private val mGlide: RequestManager,
    private val itemListener: (Mission) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_daily, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var container : View = itemView.findViewById(R.id.dailyItemBackground)
        var textFlag : TextView = itemView.findViewById(R.id.txtMarkDaily)
        var textTier : TextView = itemView.findViewById(R.id.txtMarkTier)
        var textItemTitle: TextView = itemView.findViewById(R.id.txtItemDailyTitle)
        var textItemSubtitle: TextView = itemView.findViewById(R.id.txtItemDailySubtitle)
        var textKmItem: TextView = itemView.findViewById(R.id.txtKmItemDaily)
        var textUnitItem : TextView = itemView.findViewById(R.id.txtKmUnitItemDaily)
        var textQtdItens : TextView = itemView.findViewById(R.id.txtQtdItens)
        var imgActionItem : ImageView = itemView.findViewById(R.id.imgIconItemDaily)

        init{
            container.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        //defaultVH.container.setBackgroundResource()
        defaultVH.textFlag.text = item.flagText ?: "Daily"
        defaultVH.textTier.text = item.extraFlagText
        defaultVH.textItemTitle.text = item.title
        defaultVH.textItemSubtitle.text = item.subtitle
        defaultVH.textKmItem.text = item.distance.toString()
        defaultVH.textUnitItem.text = "km"
        defaultVH.textQtdItens.text = item.qtdItens + " of " + item.qtdItens

        //IconRight
        when(item.type){
            "hasActions" -> { defaultVH.imgActionItem.setImageResource(R.drawable.ic_forward) }
            "error" -> { defaultVH.imgActionItem.setImageResource(R.drawable.icon_error) }
            "success" -> { defaultVH.imgActionItem.setImageResource(R.drawable.icon_success)}
            "warning" -> { defaultVH.imgActionItem.setImageResource(R.drawable.ic_warning) }
        }

    }
}