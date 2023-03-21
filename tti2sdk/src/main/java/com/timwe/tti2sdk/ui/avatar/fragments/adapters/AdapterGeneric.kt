package com.timwe.tti2sdk.ui.avatar.fragments.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.SetAvatar
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
    private var riveInputKey: String,
    private val avatarSetListener: (SetAvatar) -> Unit
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
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                                        isFirstResource: Boolean): Boolean {
                            viewHolder.progressGender?.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                    dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            viewHolder.progressGender?.visibility = View.GONE
                            return false
                        }
                    })
                    .into(viewHolder.imageViewGender!!)

                if(position == positionSelected){
                    viewHolder.background?.setBackgroundResource(R.drawable.bck_item_list_avatar_selected)
                }else{
                    viewHolder.background?.setBackgroundResource(R.drawable.bck_item_list_avatar_unselected)
                }

                viewHolder.imageViewGender!!.setOnClickListener {
                    avatarSetListener.invoke(
                        SetAvatar(
                            positionClick = position,
                            riveInputKey = riveInputKey,
                            riveInputValue = options.riveInputValue
                        )
                    )
                }

            }
            HeadFragment.CUSTON_VIEW_HOLDER -> {

                val viewHolder = holder as CustonViewHolder
                mGlide.load(options.imageUrl)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?,
                                                    target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            viewHolder.progressCuston?.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                    dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            viewHolder.progressCuston?.visibility = View.GONE
                            return false
                        }
                    })
                    .into(viewHolder.imageViewItem!!)

                if(position == positionSelected){
                    viewHolder.backgroundItem?.setBackgroundResource(R.drawable.bck_item_list_avatar_selected)

                }else{
                    viewHolder.backgroundItem?.setBackgroundResource(R.drawable.bck_item_list_avatar_unselected)
                }

                viewHolder.imageViewItem!!.setOnClickListener {
                    avatarSetListener.invoke(
                        SetAvatar(
                            positionClick = position,
                            riveInputKey = riveInputKey,
                            riveInputValue = options.riveInputValue
                        )
                    )
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
