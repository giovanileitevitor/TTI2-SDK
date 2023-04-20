package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.*
import com.timwe.tti2sdk.data.model.response.City
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.remote.CityDataSource

class DestinationsUseCaseImpl(
    private val cityDataSource: CityDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource,
): DestinationsUseCase {

    override suspend fun getCityInfo(cityId: Int): Results<Destination> {
        return cityDataSource.getCityInformation(
            cityId = cityId
        )
    }

    override suspend fun saveCities(listCities: ListCities) {
        sharedPrefDataSource.saveListCities(listCities)
    }

    override suspend fun getListCities(): Results<ListCities> {
        return cityDataSource.getListCity()
    }

    override suspend fun getCityId(cityNumber: Long): Long {
        return sharedPrefDataSource.getCityId(
            cityNumber = cityNumber
        )
    }

}