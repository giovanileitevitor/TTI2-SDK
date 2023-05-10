package com.timwe.tti2sdk.ui.missions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Mission2
import com.timwe.tti2sdk.ui.missions.Constants.COMPLETED
import com.timwe.tti2sdk.ui.missions.Constants.IN_PROGRESS
import com.timwe.tti2sdk.ui.missions.Constants.OPEN
import com.timwe.tti2sdk.ui.missions.Constants.REDEEMED

class DailyMissionAdapter(
    private val context: Context,
    private val data: List<Mission2> = emptyList(),
    private val tier: String? = "",
    private val mGlide: RequestManager,
    private val itemListener: (Mission2) -> Unit
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
        var textFlag1 : TextView = itemView.findViewById(R.id.txtFlag1)
        var textFlag2 : TextView = itemView.findViewById(R.id.txtFlag2)
        var textFlag3 : TextView = itemView.findViewById(R.id.txtFlag3)
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

        when(item.missionType){
            "DAILY"-> {
                defaultVH.textFlag1.text = "Daily"
                defaultVH.textFlag1.setBackgroundResource(R.drawable.background_card_daily)
                defaultVH.textFlag2.text = tier + " Tier"
                when(tier){
                    "Gold" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_gold) }
                    "Silver" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_silver) }
                    "Platinum" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_platinum)}
                    "Diamond" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_diamond)}
                    else -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_gold)}
                }
                defaultVH.textFlag2.visibility = View.VISIBLE
                if(item.threshold > 1){
                    defaultVH.textQtdItens.text = item.order.toString() + " of " + item.threshold.toString()
                }else{
                    defaultVH.textQtdItens.visibility = View.GONE
                }
            }

            "DAILY_CHECKUP", "DAILY_TIER" -> {
                defaultVH.textFlag1.text = "Daily"
                defaultVH.textFlag1.setBackgroundResource(R.drawable.background_card_daily)
                when(tier){
                    "Gold" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_gold) }
                    "Silver" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_silver) }
                    "Platinum" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_platinum)}
                    "Diamond" -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_diamond)}
                    else -> { defaultVH.textFlag2.setBackgroundResource(R.drawable.background_tier_gold)}
                }
                defaultVH.textFlag2.visibility = View.GONE
                if(item.threshold > 1){
                    defaultVH.textQtdItens.text = item.order.toString() + " of " + item.threshold.toString()
                }else{
                    defaultVH.textQtdItens.visibility = View.GONE
                }
            }

            "TARGETED" -> {
                defaultVH.textFlag1.text = "Adventure"
                defaultVH.textFlag1.setBackgroundResource(R.drawable.background_card_adventure)
                defaultVH.textQtdItens.visibility = View.GONE
                defaultVH.textFlag2.visibility = View.GONE
            }
            "BOOSTER" -> {
                defaultVH.textFlag1.text = "Booster"
                defaultVH.textFlag1.setBackgroundResource(R.drawable.background_card_booster)
                defaultVH.textQtdItens.visibility = View.GONE
                defaultVH.textFlag2.visibility = View.GONE
            }
        }

        defaultVH.textItemTitle.text = item.name
        defaultVH.textItemSubtitle.text = item.description
        defaultVH.textKmItem.text = item.rewards[0].prizeValue ?: "0"
        defaultVH.textUnitItem.text = "km"

        //Icon card
        when(item.status){
            OPEN -> { defaultVH.imgActionItem.setImageResource(R.drawable.ic_forward) }
            IN_PROGRESS -> { defaultVH.imgActionItem.setImageResource(R.drawable.ic_forward) }
            COMPLETED -> {
                defaultVH.imgActionItem.setImageResource(R.drawable.icon_success)
                defaultVH.container.setBackgroundResource(R.drawable.background_line_soft_green)
            }
            REDEEMED -> {
                defaultVH.imgActionItem.setImageResource(R.drawable.icon_success)
                defaultVH.container.setBackgroundResource(R.drawable.background_line_soft_green)
            }
            else -> defaultVH.imgActionItem.visibility = View.GONE
        }

    }

}