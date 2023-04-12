package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.*
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.remote.CityDataSource

class DestinationsUseCaseImpl(
    private val cityDataSource: CityDataSource
): DestinationsUseCase {

    override suspend fun getCityInfo(cityId: Int): Results<CityInfo> {
        return cityDataSource.getCityInformation(
            cityId = cityId
        )
    }

    override suspend fun getPlacesFiltered(type: String): List<Place> {
        return generatePlaces(5)
    }

}