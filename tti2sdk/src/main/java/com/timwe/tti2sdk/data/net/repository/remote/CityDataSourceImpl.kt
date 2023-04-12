package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.CityInfo
import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.CityInfoResponseToCityInfo
import com.timwe.tti2sdk.data.net.mapper.ListCityResponseToListCity
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.utils.Utils

class CityDataSourceImpl(
    private val api: API,
    private val cityInfoResponseToCityInfo: CityInfoResponseToCityInfo,
    private val listCityResponseToListCity: ListCityResponseToListCity
): CityDataSource {

    override suspend fun getCityInformation(cityId: Int): Results<CityInfo> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}cities/$cityId")
        return api.getCityInfo(
            cityId = cityId
        ).create(cityInfoResponseToCityInfo)
    }

    override suspend fun getListCity(): Results<ListCities> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}cities")
        return api.getCityList().create(listCityResponseToListCity)

    }

}