package com.timwe.tti2sdk.data.entity

data class Destination(
    val id: Int,
    val title: String,
    val subtitle: String,
    val description: String,
    val urlLink: String
)

fun generateDestination() = Destination(
    id = 1,
    title = "Toba Lake",
    subtitle = "Trivia",
    description = "Lake Toba is a large natural lake in North Sumatra, Indonesia, occupying the caldera of a supervolcano. The lake is located in the middle of the northern part of the island of Sumatra, with a surface elevation of about 900 metres. The lake is about 62 miles long, 19 mi wide, and up to 1,657 ft deep. It is the largest lake in Indonesia and the largest volcanic lake in the world.Toba Caldera is one of twenty Geoparks in Indonesia, and was recognised in July 2020 as one of the UNESCO Global Geoparks.",
    urlLink = "https://en.wikipedia.org/wiki/Lake_Toba"
)