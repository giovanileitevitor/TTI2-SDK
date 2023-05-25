package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.data.model.response.*
import com.timwe.tti2sdk.data.net.data.Mapper

class CityInfoResponseToDestination : Mapper<CityInfoResponse, Destination>(){

    override fun transform(item: CityInfoResponse): Destination {

        return Destination(
            id = item.city.id,
            title = item.city.name,
            description = item.city.trivia,
            images = item.city.cardImageUrl,
            urlLink = if (item.city.wikipedia?.imageUrl != null) item.city.wikipedia.imageUrl else "",
            isCapital = item.city.capital,
            shareImageUrl = item.city.shareImageUrl,
            xpos = item.city.xpos,
            ypos = item.city.ypos,
            order = item.city.order,
            cityCode = "A C E H",
            prizes = getPrizes(item = item),
            placesAll = getListByID("ALL", item),
            placesFood = getListByID("FOOD", item ),
            placesSights = getListByID("SIGHTS", item),
            placesStays = getListByID("STAYS", item),
        )

        //se nao capital sempre Trivia sub titulo

        //descricao se capital
        //about
        //background

        //se capital =  dois layouts -- about e  background
        //com array de imagem em cadrimages
        //se capital nao tem arround-here


        //Arround HEre fazer dinamico
        //adpatar conforme vem do json
        //pode ter 4 ou menos itens


    }

}

private fun getPrizes(item: CityInfoResponse): List<Prize>{
    val prizes = arrayListOf<Prize>()

    item.city.rewardList.forEach{
        prizes.add(
            Prize(
                id = 1,
                prizeImg = it.iconUrl,
                prizeText = it.description ?: "default",
                isPrizeChecked = true
            )
        )
    }

    return prizes
}

fun getListByID(id: String, cityInfoResponse: CityInfoResponse): List<Wikipedia>{
    try {
        return cityInfoResponse.city.aroundHere.filter {  it.category.name == id }.first().destinations
    }catch (e: java.lang.Exception){
        return listOf()
    }
}