package com.timwe.tti2sdk.ui.prizes.fragments.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.GenericViewHolder

class HistoryBottomViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var tvTitleBottom: TextView? = null
    var tvMessageBottom: TextView? = null
    var tvHoursBottom: TextView? = null
    var ivIconBottom: ImageView? = null

    init{
        tvTitleBottom = itemView.findViewById(R.id.titleBottom)
        tvMessageBottom = itemView.findViewById(R.id.messageBottom)
        tvHoursBottom = itemView.findViewById(R.id.hoursBottom)
        ivIconBottom = itemView.findViewById(R.id.ivIconBottom)
    }

}