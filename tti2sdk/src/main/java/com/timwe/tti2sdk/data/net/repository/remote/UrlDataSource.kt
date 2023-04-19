package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.Results

interface UrlDataSource {

    suspend fun getUrls(msisdn: Long, lang: String): Results<UrlAddress>

}