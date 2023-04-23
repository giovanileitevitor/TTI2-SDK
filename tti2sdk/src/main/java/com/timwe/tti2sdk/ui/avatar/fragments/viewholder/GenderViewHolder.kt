package com.timwe.tti2sdk.ui.avatar.fragments.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.timwe.tti2sdk.R

class GenderViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var background: View? = null
    var textGender: TextView? = null
    var imageViewGender: ImageView? = null
    var progressGender: View? = null

    init {
        progressGender = itemView.findViewById(R.id.progressBarItemAvatar);
        background = itemView.findViewById(R.id.backgroundItemListAvatar);
        textGender = itemView.findViewById(R.id.textViewAvatarSelected);
        imageViewGender = itemView.findViewById(R.id.imageViewItemAvatarSelected);
    }

}