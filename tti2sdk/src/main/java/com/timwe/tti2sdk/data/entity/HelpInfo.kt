package com.timwe.tti2sdk.data.entity

import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.timwe.tti2sdk.R

data class HelpInfo(
    var id: Int,
    var imageDrawable: Int,
    var txtTitleHelp: String,
    var txtSubtitleHelp: String
)

fun generateHelpInfo(qtd: Int): List<HelpInfo>{
    val helps = ArrayList<HelpInfo>()

    for(i in 1..qtd){
        helps.add(
            HelpInfo(
                id = i,
                imageDrawable = R.drawable.logo_help_activity,
                txtTitleHelp = "TESTE",
                txtSubtitleHelp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sit amet lectus a mi lobortis iaculis. Mauris odio tortor, accumsan vel gravida sit amet, malesuada a tortor. Praesent efficitur eleifend eros quis elementum."
            )
        )
    }

    return helps
}
