package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Place
import com.timwe.tti2sdk.data.entity.generateDestination
import com.timwe.tti2sdk.data.entity.generatePlaces

class DestinationsUseCaseImpl(

): DestinationsUseCase {

    override suspend fun getDestinationDetails(id: Int): Destination {
        return generateDestination()
    }

    override suspend fun getPlacesFiltered(type: String): List<Place> {
        return generatePlaces(5)
    }

}