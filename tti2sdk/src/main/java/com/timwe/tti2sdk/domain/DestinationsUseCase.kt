package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Place

interface DestinationsUseCase {

    suspend fun getDestinationDetails(id: Int): Destination

    suspend fun getPlacesFiltered(type: String): List<Place>

}