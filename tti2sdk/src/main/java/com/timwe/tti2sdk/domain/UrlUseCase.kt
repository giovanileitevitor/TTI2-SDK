package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.Results

interface UrlUseCase {

    suspend fun getUrls(): Results<UrlAddress>

    suspend fun saveUrls(urlAddress: UrlAddress)

    suspend fun saveCities(listCities: ListCities)

    suspend fun getListCities(): Results<ListCities>

}