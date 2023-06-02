package com.timwe.tti2sdk.ui.destinations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Prize

class PrizeAdapter(
    private val context: Context,
    private val data: List<Prize>,
    private val mGlide: RequestManager,
    private val itemListener: (Prize) -> Unit,
    private val darkItens: Boolean = false
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_line_destinations_prize, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position, darkItens)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val containerPrize: ConstraintLayout = itemView.findViewById(R.id.containerPrize)
        val backgrounrPrize: View = itemView.findViewById(R.id.prize_one_background)
        val txtPrizeTitle: TextView = itemView.findViewById(R.id.prize_one_title_box)
        val imgPrizeMessage: TextView = itemView.findViewById(R.id.prize_one_sub_title_box)
        val isPrizeImage: ImageView = itemView.findViewById(R.id.prize_one_icon_pre_text)

        init{
            containerPrize.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int, darkItens: Boolean = false){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        if(darkItens){
            defaultVH.containerPrize.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            defaultVH.backgrounrPrize.background = AppCompatResources.getDrawable(context, R.drawable.background_line_dark_blue)
            defaultVH.txtPrizeTitle.setTextColor(ContextCompat.getColor(context, R.color.all_white))
            defaultVH.imgPrizeMessage.setTextColor(ContextCompat.getColor(context, R.color.all_white))
        }

        //Nao exibe o card se a imagem estiver nula ou vazia
        if(!item.prizeImg.isNullOrEmpty()){

            mGlide
                .load(item.prizeImg)
                .into(defaultVH.isPrizeImage)

            defaultVH.txtPrizeTitle.text = item.prizeText
            defaultVH.imgPrizeMessage.text = item.prizeText

        }else{

            defaultVH.isPrizeImage.visibility = View.INVISIBLE

            defaultVH.txtPrizeTitle.text = item.prizeText
            defaultVH.imgPrizeMessage.text = item.prizeText
        }

    }

}