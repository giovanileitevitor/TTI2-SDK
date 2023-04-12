package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.model.response.City
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.CityInfoResponseToDestination
import com.timwe.tti2sdk.data.net.mapper.ListCityResponseToListCity
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.utils.Utils

class CityDataSourceImpl(
    private val api: API,
    private val cityInfoResponseToDestination: CityInfoResponseToDestination,
    private val listCityResponseToListCity: ListCityResponseToListCity
): CityDataSource {

    override suspend fun getCityInformation(cityId: Int): Results<Destination> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}cities/$cityId")
        return api.getCityInfo(
            cityId = cityId
        ).create(cityInfoResponseToDestination)
    }

    override suspend fun getListCity(): Results<ListCities> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}cities")
        return api.getCityList().create(listCityResponseToListCity)
    }

}