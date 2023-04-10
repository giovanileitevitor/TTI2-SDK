package com.timwe.tti2sdk.ui.prizes.fragments.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.GenericViewHolder

class HistoryTopViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var tvTitle: TextView? = null

    var tvTitleTop: TextView? = null
    var tvMessageTop: TextView? = null
    var tvHoursTop: TextView? = null
    var ivIconTop: ImageView? = null

    init{
        tvTitle = itemView.findViewById(R.id.tvHeader)

        tvTitleTop = itemView.findViewById(R.id.titleTop)
        tvMessageTop = itemView.findViewById(R.id.messageTop)
        tvHoursTop = itemView.findViewById(R.id.hoursTop)
        ivIconTop = itemView.findViewById(R.id.ivIconTop)
    }

}