package com.timwe.tti2sdk.ui.destinations.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Prize

class PrizeAdapter(
    private val data: List<Prize> = emptyList(),
    private val mGlide: RequestManager,
    private val itemListener: (Prize) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_prize, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView = itemView.findViewById(R.id.imgPrize)
        val title: TextView = itemView.findViewById(R.id.txtTitlePrize)
        val subtitle: TextView = itemView.findViewById(R.id.txtSubtitlePrize)

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

        mGlide.load(item.imgPrize)
            .into(defaultVH.img)

        defaultVH.title.text = item.titlePrize
        defaultVH.subtitle.text = item.detailsPrize
    }
}