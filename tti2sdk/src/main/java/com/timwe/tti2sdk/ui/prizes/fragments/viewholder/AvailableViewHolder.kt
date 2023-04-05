package com.timwe.tti2sdk.ui.prizes.fragments.viewholder

import android.text.Layout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.ui.avatar.fragments.viewholder.GenericViewHolder

class AvailableViewHolder(itemView: View): GenericViewHolder(itemView = itemView) {
    var ivIconTop: ImageView? = null
    var tvTitleVoucher: TextView? = null
    var btnPrize: AppCompatButton? = null
    var progress: View? = null
    var containerTotal: ConstraintLayout? = null

    init{
        progress = itemView.findViewById(R.id.progressBarItemAvatar)
        ivIconTop = itemView.findViewById(R.id.ivIconTop)
        tvTitleVoucher = itemView.findViewById(R.id.titleVoucher)
        btnPrize = itemView.findViewById(R.id.btnConfirmPrize)
        containerTotal = itemView.findViewById(R.id.containerTotal)
    }

}