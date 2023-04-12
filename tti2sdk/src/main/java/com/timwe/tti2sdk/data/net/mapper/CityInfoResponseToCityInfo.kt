package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.CityInfo
import com.timwe.tti2sdk.data.model.response.CityInfoResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class CityInfoResponseToCityInfo : Mapper<CityInfoResponse, CityInfo>(){

    override fun transform(item: CityInfoResponse): CityInfo {
        //TODO - terminar de modelar o mapeamento das informaçoes usadas na tela DestinationActivity

        return CityInfo(
            id = item.id
        )

    }

}