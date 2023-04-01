package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.generateDestination
import com.timwe.tti2sdk.data.net.api.Results

class DestinationsUseCaseImpl(

): DestinationsUseCase {

    override suspend fun getDestinationDetails(id: Int): Destination {
        return generateDestination()
    }

}