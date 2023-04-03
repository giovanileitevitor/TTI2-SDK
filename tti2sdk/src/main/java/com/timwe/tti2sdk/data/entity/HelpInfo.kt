package com.timwe.tti2sdk.data.entity

data class HelpInfo(
    var id: Int,
    var imgAddres: String,
    var txtTitleHelp: String,
    var txtSubtitleHelp: String
)

fun generateHelpInfo(qtd: Int): List<HelpInfo>{
    val helps = ArrayList<HelpInfo>()

    for(i in 1..qtd){
        helps.add(
            HelpInfo(
                id = i,
                imgAddres = "https://source.unsplash.com/random/800x800/?img=1",
                txtTitleHelp = "TESTE",
                txtSubtitleHelp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus sit amet lectus a mi lobortis iaculis. Mauris odio tortor, accumsan vel gravida sit amet, malesuada a tortor. Praesent efficitur eleifend eros quis elementum."
            )
        )
    }

    return helps
}
