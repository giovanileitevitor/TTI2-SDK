package com.timwe.tti2sdk.ui.destinations.adapters

import android.content.Context
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
    private val context: Context,
    private val data: List<Prize> = emptyList(),
    private val mGlide: RequestManager,
    private val itemListener: (Prize) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_prize_dest, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var isSelectedPrize: View = itemView.findViewById(R.id.backgroundItemPrize)
        var imgPrize: ImageView = itemView.findViewById(R.id.imgPrize)
        var txtPrize: TextView = itemView.findViewById(R.id.txtPrizeName)
        var isPrizeChecked: ImageView = itemView.findViewById(R.id.imgChecked)

        init{
            imgPrize.setOnClickListener {
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
            .load(item.prizeImg)
            .into(defaultVH.imgPrize)

        defaultVH.txtPrize.text = item.prizeText
        defaultVH.isPrizeChecked.visibility = if(item.isPrizeChecked) View.VISIBLE else View.GONE

        //TODO - implementar a lógica para mudar o background e a imagem do card
        defaultVH.isSelectedPrize.background = context.getDrawable(R.drawable.bck_item_list_prize_selected)
    }

}