package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.CityInfo
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.Place
import com.timwe.tti2sdk.data.net.api.Results

interface DestinationsUseCase {

    suspend fun getCityInfo(cityId: Int): Results<CityInfo>

    suspend fun getPlacesFiltered(type: String): List<Place>

}