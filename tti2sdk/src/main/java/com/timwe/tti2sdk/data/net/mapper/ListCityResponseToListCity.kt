package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.ListCities
import com.timwe.tti2sdk.data.model.response.ListCityResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class ListCityResponseToListCity: Mapper<ListCityResponse, ListCities>(){

    override fun transform(item: ListCityResponse): ListCities {
        return ListCities(
            listCities = item.cityList
        )
    }

}
