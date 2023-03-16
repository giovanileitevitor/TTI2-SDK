package com.timwe.tti2sdk.ui.avatar.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.model.response.Options
import com.timwe.tti2sdk.ui.avatar.fragments.HeadFragment
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.GenderViewHolder
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.CustonViewHolder

class AdapterGeneric(
    private val context: Context,
    private val resource: Int,
    private var data: List<Options>,
    private val mGlide: RequestManager,
    private val typeViewHolder: Int,
    private var positionSelected: Int = 0,
    private val genderListener: (Int) -> Unit
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(resource, parent, false)

        return when(typeViewHolder){
            HeadFragment.GENDER_VIEW_HOLDER -> {
                GenderViewHolder(viewHolder)
            }
            HeadFragment.CUSTON_VIEW_HOLDER -> {
                CustonViewHolder(viewHolder)
            }
            else -> {
                throw Exception("View holder not exists")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val options = data[position]

        when(typeViewHolder){
            HeadFragment.GENDER_VIEW_HOLDER -> {

                val viewHolder = holder as GenderViewHolder
                viewHolder.textGender?.text = options.criteria
                mGlide.load(options.imageUrl)
                    .placeholder(R.drawable.icon_star)
                    .into(viewHolder.imageViewGender!!)

                if(position == positionSelected){
                    viewHolder.background?.setBackgroundResource(R.drawable.bck_item_list_avatar_selected)
                }else{
                    viewHolder.background?.setBackgroundResource(R.drawable.bck_item_list_avatar_unselected)
                }

                viewHolder.imageViewGender!!.setOnClickListener {
                    genderListener?.invoke(position)
                }

            }
            HeadFragment.CUSTON_VIEW_HOLDER -> {

                val viewHolder = holder as CustonViewHolder
                mGlide.load(options.imageUrl)
                    .placeholder(R.drawable.icon_star)
                    .into(viewHolder.imageViewItem!!)

                if(position == positionSelected){
                    viewHolder.backgroundItem?.setBackgroundResource(R.drawable.bck_item_list_avatar_selected)

                }else{
                    viewHolder.backgroundItem?.setBackgroundResource(R.drawable.bck_item_list_avatar_unselected)
                }

                viewHolder.imageViewItem!!.setOnClickListener {
                    genderListener?.invoke(position)
                }

            }
            else -> {
                throw Exception("View holder not exists")
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setNewPositionClicked(positionClick: Int){
        positionSelected = positionClick
        notifyDataSetChanged()
    }

    fun setNewOptionsPosition(newPositionSelected: Int, newListOptions: List<Options>){
        data = newListOptions
        positionSelected = newPositionSelected
        notifyDataSetChanged()
    }

}
