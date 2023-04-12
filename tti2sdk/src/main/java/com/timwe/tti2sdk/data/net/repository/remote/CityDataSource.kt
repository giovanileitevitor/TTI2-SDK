package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.CityInfo
import com.timwe.tti2sdk.data.net.api.Results

interface CityDataSource {

    suspend fun getCityInformation(cityId: Int): Results<CityInfo>

}