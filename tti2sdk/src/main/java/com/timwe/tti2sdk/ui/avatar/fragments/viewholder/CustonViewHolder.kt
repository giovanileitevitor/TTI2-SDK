package com.timwe.tti2sdk.ui.avatar.fragments.viewholder

import android.view.View
import android.widget.ImageView
import com.timwe.tti2sdk.R

/*
*
* Usado como view holder de
* Skin Color
* Hair
* Eye Color
* Eyebrows
* Top
* Bottoms
* Style
*
* */
class CustonViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var backgroundItem: View? = null
    var imageViewItem: ImageView? = null

    init {
        backgroundItem = itemView.findViewById(R.id.backgroundItemListGeneric);
        imageViewItem = itemView.findViewById(R.id.imageViewItemGeneric);
    }

}