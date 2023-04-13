package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.R

data class OnboardingInfo(
    var id: Int,
    var imageDrawable: Int,
    var txtTitleHelp: String,
    var txtSubtitleHelp: String,
    var hasButton: Boolean,
    var buttonText: String = ""
)

fun generateOnboardingInfo(qtd: Int): List<OnboardingInfo>{
    val helps = ArrayList<OnboardingInfo>()

    for(i in 1..qtd){
        helps.add(
            OnboardingInfo(
                id = i,
                imageDrawable = R.drawable.logo_help_activity,
                txtTitleHelp = "TESTE",
                txtSubtitleHelp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sit amet lectus a mi lobortis iaculis. Mauris odio tortor, accumsan vel gravida sit amet, malesuada a tortor. Praesent efficitur eleifend eros quis elementum.",
                hasButton = true,
                buttonText = ""
            )
        )
    }

    return helps
}
