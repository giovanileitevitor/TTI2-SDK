package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.Destination
import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.net.api.Results

interface CityDataSource {

    suspend fun getCityInformation(cityId: Int): Results<Destination>

    suspend fun getListCity(): Results<ListCities>

}