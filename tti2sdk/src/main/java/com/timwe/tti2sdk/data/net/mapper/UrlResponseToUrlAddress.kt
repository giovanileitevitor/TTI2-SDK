package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.model.response.UrlResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class UrlResponseToUrlAddress: Mapper<UrlResponse, UrlAddress>() {

    override fun transform(item: UrlResponse): UrlAddress {
        return UrlAddress(
            id = item.id,
            urls = convert(item.urls)
        )
    }

    private fun convert(itens: List<String>): List<String>{
        val urls = arrayListOf<String>()
        itens.forEach {
            urls.add(it)
        }

        return urls
    }

}