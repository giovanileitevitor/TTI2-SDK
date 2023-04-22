package com.timwe.tti2sdk.ui.destinations.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.model.response.Wikipedia

class PlaceAdapter(
    private var data: List<Wikipedia> = emptyList(),
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
        var imgAroundHere: ImageView = itemView.findViewById(R.id.imgAroundHere)
        val type: TextView = itemView.findViewById(R.id.txtType)
        val placeName: TextView = itemView.findViewById(R.id.txtNamePlace)

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

        mGlide.load(item.imageUrl)
            .into(defaultVH.imgAroundHere)

        defaultVH.type.text = item.category
        defaultVH.placeName.text = item.name
    }

    fun replaceItens(newData: List<Wikipedia>){
        data = newData
        notifyDataSetChanged()
    }


}