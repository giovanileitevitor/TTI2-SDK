package com.timwe.tti2sdk.ui.onboarding.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.HelpInfo

class OnBoardingAdapter(
    private val data: List<HelpInfo>,
    private val mGlide: RequestManager,
    private val itemListener: (HelpInfo) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_help, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
       processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var helpImage: ImageView = itemView.findViewById(R.id.imgHelpInfo)
        var txtTitleHelp: TextView = itemView.findViewById(R.id.txtTitleHelp)
        var txtSubtitleHelp: TextView = itemView.findViewById(R.id.txtSubtitleHelp)
        var btnCreateProfile: Button = itemView.findViewById(R.id.btnCreateProfile)

        init{
            btnCreateProfile.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as DefaultVH

        mGlide.load(item.imageDrawable).into(defaultVH.helpImage)

        defaultVH.txtTitleHelp.text = item.txtTitleHelp
        defaultVH.txtSubtitleHelp.text = item.txtSubtitleHelp

        if(item.hasButton){
            defaultVH.btnCreateProfile.visibility = View.VISIBLE
            defaultVH.btnCreateProfile.text = item.buttonText
        }else{
            defaultVH.btnCreateProfile.visibility = View.GONE
        }
    }
}