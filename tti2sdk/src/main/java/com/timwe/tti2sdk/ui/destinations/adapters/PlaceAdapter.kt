package com.timwe.tti2sdk.ui.destinations.adapters

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
import com.timwe.tti2sdk.data.model.response.Wikipedia

class PlaceAdapter(
    private val context: Context,
    private var data: List<Wikipedia>,
    private val mGlide: RequestManager,
    private val itemListener: (Wikipedia) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_around_here, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val containerPlace: ConstraintLayout = itemView.findViewById(R.id.containerPlace)
        val imgAroundHere: ImageView = itemView.findViewById(R.id.imgAroundHere)
        val type: TextView = itemView.findViewById(R.id.txtType)
        val placeName: TextView = itemView.findViewById(R.id.txtNamePlace)
        val typeIcon: ImageView = itemView.findViewById(R.id.imgTypeIcon)

        init{
            containerPlace.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        mGlide
            .load(item.imageUrl)
            .into(defaultVH.imgAroundHere)

        defaultVH.imgAroundHere.scaleType = ImageView.ScaleType.CENTER_CROP
        defaultVH.type.text = item.category
        defaultVH.placeName.text = item.name

        when(item.category){
            "All"-> { defaultVH.typeIcon.setImageResource(R.drawable.icon_all_white) }
            "FOOD" -> { defaultVH.typeIcon.setImageResource(R.drawable.icon_food_white)}
            "SIGHTS" -> { defaultVH.typeIcon.setImageResource(R.drawable.icon_sights_white)}
            "STAYS" -> { defaultVH.typeIcon.setImageResource(R.drawable.icon_stays_white)}
            else -> { defaultVH.typeIcon.setImageResource(R.drawable.icon_all_white)}
        }
    }

    fun replaceItens(newData: List<Wikipedia>){
        data = newData
        notifyDataSetChanged()
    }

}