package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.CityInfo
import com.timwe.tti2sdk.data.model.response.CityInfoResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class CityInfoResponseToCityInfo : Mapper<CityInfoResponse, CityInfo>(){

    override fun transform(item: CityInfoResponse): CityInfo {

        return CityInfo(
            id = item.id
        )

    }

}