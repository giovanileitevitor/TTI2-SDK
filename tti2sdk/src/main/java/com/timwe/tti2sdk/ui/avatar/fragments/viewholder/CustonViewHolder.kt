package com.timwe.tti2sdk.ui.avatar.fragments.viewholder

import android.view.View
import android.widget.ImageView
import com.timwe.tti2sdk.R

class CustonViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var backgroundItem: View? = null
    var imageViewItem: ImageView? = null
    var progressCuston: View? = null

    init {
        progressCuston = itemView.findViewById(R.id.progressBarItemCuston);
        backgroundItem = itemView.findViewById(R.id.backgroundItemListGeneric);
        imageViewItem = itemView.findViewById(R.id.imageViewItemGeneric);
    }

}