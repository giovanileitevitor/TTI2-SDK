package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.net.api.Results

interface DestinationsUseCase {

    suspend fun getCityInfo(cityId: Int): Results<Destination>

    suspend fun saveCities(listCities: ListCities)

    suspend fun getListCities(): Results<ListCities>

    suspend fun getCityId(cityNumber: Long): Long

}