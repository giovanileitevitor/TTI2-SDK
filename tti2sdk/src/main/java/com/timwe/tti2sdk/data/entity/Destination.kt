package com.timwe.tti2sdk.data.entity

data class Destination(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val urlLink: String,
    val prizes: List<Prize> = emptyList(),
    val places: List<Place> = emptyList()
)

data class Prize(
    val id: Int,
    val titlePrize: String,
    val detailsPrize: String,
    val imgPrize: String? = ""
)

data class Place(
    val id: Int,
    val type: String,
    val typeName: String,
    val titlePlace: String,
    val imgPlace: String? = ""
)

fun generateDestination() = Destination(
    id = 1,
    title = "Toba Lake",
    subtitle = "Trivia",
    description = "Lake Toba is a large natural lake in North Sumatra, Indonesia, occupying the caldera of a supervolcano. The lake is located in the middle of the northern part of the island of Sumatra, with a surface elevation of about 900 metres. The lake is about 62 miles long, 19 mi wide, and up to 1,657 ft deep. It is the largest lake in Indonesia and the largest volcanic lake in the world.Toba Caldera is one of twenty Geoparks in Indonesia, and was recognised in July 2020 as one of the UNESCO Global Geoparks.",
    urlLink = "https://en.wikipedia.org/wiki/Lake_Toba",
    prizes = generatePrizes(2),
    places = generatePlaces(5)
)

fun generatePrizes(qtd: Int):  List<Prize>{
    val prizes = ArrayList<Prize>()
    for(i in 1..qtd){
        prizes.add(
            Prize(
                id = i,
                titlePrize = if(i%2 !=0) "Zalora Voucher" else "Toba Lake",
                detailsPrize = if(i%2 !=0) "Valid until 30 Dec 2023" else "Badge",
                imgPrize = "https://johncodeos.com/wp-content/uploads/2021/01/core-data-ios-tutorial-thumbnail-1024x576.png"
            )
        )
    }

    return prizes
}

fun generatePlaces(qtd: Int): List<Place>{
    val places = ArrayList<Place>()

    for(i in 1..qtd){
        places.add(
            Place(
                id = i,
                type = if(i%2 !=0) "All" else "Foods",
                typeName = if(i%2 !=0) "All" else "Foods",
                titlePlace = if(i%2 !=0) "TaTaring Coffee & Resto" else "Bukit Sibea-bea",
                imgPlace = ""
            )
        )
    }

    return places
}