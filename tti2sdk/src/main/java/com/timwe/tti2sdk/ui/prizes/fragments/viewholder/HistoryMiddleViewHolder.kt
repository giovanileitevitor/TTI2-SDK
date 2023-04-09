package com.timwe.tti2sdk.ui.prizes.fragments.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.GenericViewHolder

class HistoryMiddleViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var tvTitleMiddle: TextView? = null
    var tvMessageMiddle: TextView? = null
    var tvHoursMiddle: TextView? = null
    var ivIconMiddle: ImageView? = null

    init{
        tvTitleMiddle = itemView.findViewById(R.id.titleMiddle)
        tvMessageMiddle = itemView.findViewById(R.id.messageMiddle)
        tvHoursMiddle = itemView.findViewById(R.id.hoursMiddle)
        ivIconMiddle = itemView.findViewById(R.id.ivIconMiddle)
    }

}