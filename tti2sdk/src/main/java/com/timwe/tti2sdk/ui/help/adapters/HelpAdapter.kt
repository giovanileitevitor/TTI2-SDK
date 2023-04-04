package com.timwe.tti2sdk.ui.help.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.HelpInfo

class HelpAdapter(
    private val data: List<HelpInfo>,
    private val mGlide: RequestManager,
): RecyclerView.Adapter<HelpAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_help, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val img = holder.itemView.findViewById<ImageView>(R.id.imgHelpInfo)
        val txtTitleHelp = holder.itemView.findViewById<TextView>(R.id.txtTitleHelp)
        val subTitleHelp = holder.itemView.findViewById<TextView>(R.id.txtSubtitleHelp)

        mGlide.load(data[position].imageDrawable).into(img)
        txtTitleHelp.text = data[position].txtTitleHelp
        subTitleHelp.text = data[position].txtSubtitleHelp
    }

    override fun getItemCount(): Int {
        return data.size
    }
}