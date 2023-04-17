package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.data.model.response.AroundHere
import com.timwe.tti2sdk.data.model.response.Wikipedia
import java.io.Serializable

data class Destination(
    val id: Long,
    val title: String,
    val description: String,
    val imageTop: String,
    val urlLink: String,
    val isCapital: Boolean,
    val xpos: String,
    val ypos: String,
    val placesAll: List<Wikipedia> = emptyList(),
    val placesFood: List<Wikipedia> = emptyList(),
    val placesSights: List<Wikipedia> = emptyList(),
    val placesStays: List<Wikipedia> = emptyList(),
): Serializable
