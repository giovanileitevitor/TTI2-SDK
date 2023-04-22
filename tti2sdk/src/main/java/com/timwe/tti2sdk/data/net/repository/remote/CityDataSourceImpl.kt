package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.CityInfoResponseToDestination
import com.timwe.tti2sdk.data.net.mapper.ListCityResponseToListCity
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.utils.Utils

class CityDataSourceImpl(
    private val api: API,
    private val cityInfoResponseToDestination: CityInfoResponseToDestination,
    private val listCityResponseToListCity: ListCityResponseToListCity,
    private val context: Context,
): CityDataSource {

    override suspend fun getCityInformation(cityId: Long): Results<Destination> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}cities/$cityId")
        return api.getCityInfo(
            msisdn = (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang = (context as Application).getUserProfile().lang!!,
            tier = (context as Application).getUserProfile().tier!!,
            cityId = cityId
        ).create(cityInfoResponseToDestination)
    }

    override suspend fun getListCity(): Results<ListCities> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}cities")
        Utils.showLog("SDK", "Request: ${(context as Application).getUserProfile().userMsisdn!!.toLong()}")
        return api.getCityList(
            msisdn = (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang = (context as Application).getUserProfile().lang!!,
            tier = (context as Application).getUserProfile().tier!!,
        ).create(listCityResponseToListCity)
    }

}