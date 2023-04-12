package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.model.response.*
import com.timwe.tti2sdk.data.net.data.Mapper

class CityInfoResponseToDestination : Mapper<CityInfoResponse, Destination>(){

    override fun transform(item: CityInfoResponse): Destination {

        return Destination(
            id = item.city.id,
            name = item.city.name,
            title = "",
            subtitle = "",
            description = item.city.trivia,
            imageTop = if (item.city.cardImageURL != null && item.city.cardImageURL.isNotEmpty()) item.city.cardImageURL[0] else "",
            urlLink = if (item.city.wikipedia != null && item.city.wikipedia.imageURL != null) item.city.wikipedia.imageURL else "",
            isCapital = item.city.capital,
            xpos = item.city.xpos,
            ypos = item.city.ypos,
            placesAll = getListByID("ALL", item),
            placesFood = getListByID("FOOD", item ),
            placesSights = getListByID("SIGHTS", item),
            placesStays = getListByID("STAYS", item),
        )

        //TODO perguntar sobre o campo title
        //TODO perguntar sobre o campo subtitletitle
        //TODO perguntar sobre o campo imageTop
        //TODO perguntar sobre o campo urlLink

        //TODO imageURL dentro do adapter, que eh do wikepedia

    }

}

fun getListByID(id: String, cityInfoResponse: CityInfoResponse): List<Wikipedia>{
    try {
        return cityInfoResponse.city.aroundHere.filter {  it.category.name == id }.first().destinations
    }catch (e: java.lang.Exception){
        return listOf()
    }
}