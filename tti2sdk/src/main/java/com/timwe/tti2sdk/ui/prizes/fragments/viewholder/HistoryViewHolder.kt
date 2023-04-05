package com.timwe.tti2sdk.ui.prizes.fragments.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.GenericViewHolder

class HistoryViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var tvTitle: TextView? = null

    var tvTitleTop: TextView? = null
    var tvMessageTop: TextView? = null
    var tvHoursTop: TextView? = null
    var ivIconTop: ImageView? = null

    var tvTitleMiddle: TextView? = null
    var tvMessageMiddle: TextView? = null
    var tvHoursMiddle: TextView? = null
    var ivIconMiddle: ImageView? = null

    var tvTitleBottom: TextView? = null
    var tvMessageBottom: TextView? = null
    var tvHoursBottom: TextView? = null
    var ivIconBottom: ImageView? = null

    init{
        tvTitle = itemView.findViewById(R.id.tvHeader)

        tvTitleTop = itemView.findViewById(R.id.titleTop)
        tvMessageTop = itemView.findViewById(R.id.messageTop)
        tvHoursTop = itemView.findViewById(R.id.hoursTop)
        ivIconTop = itemView.findViewById(R.id.ivIconTop)

        tvTitleMiddle = itemView.findViewById(R.id.titleMiddle)
        tvMessageMiddle = itemView.findViewById(R.id.messageMiddle)
        tvHoursMiddle = itemView.findViewById(R.id.hoursMiddle)
        ivIconMiddle = itemView.findViewById(R.id.ivIconMiddle)

        tvTitleBottom = itemView.findViewById(R.id.titlebottom)
        tvMessageBottom = itemView.findViewById(R.id.messageBottom)
        tvHoursBottom = itemView.findViewById(R.id.hoursBottom)
        ivIconBottom = itemView.findViewById(R.id.ivIconBottom)
    }

}