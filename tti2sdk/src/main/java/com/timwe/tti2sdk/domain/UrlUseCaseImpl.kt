package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.remote.CityDataSource
import com.timwe.tti2sdk.data.net.repository.remote.UrlDataSource

class UrlUseCaseImpl(
    private val urlDataSource: UrlDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource,
    private val cityDataSource: CityDataSource
): UrlUseCase {

    override suspend fun getUrls(): Results<UrlAddress> {
        return urlDataSource.getUrls()
    }

    override suspend fun saveUrls(UrlAddress: UrlAddress){
        sharedPrefDataSource.saveUrls(UrlAddress)
    }

    override suspend fun saveCities(listCities: ListCities) {
        sharedPrefDataSource.saveListCities(listCities)
    }

    override suspend fun getListCities(): Results<ListCities> {
        return cityDataSource.getListCity()
    }

}