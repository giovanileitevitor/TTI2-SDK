package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Destination

interface DestinationsUseCase {

    suspend fun getDestinationDetails(id: Int): Destination


}