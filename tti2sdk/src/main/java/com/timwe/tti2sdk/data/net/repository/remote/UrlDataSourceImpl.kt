package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.UrlResponseToUrlAddress
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.utils.Utils

class UrlDataSourceImpl(
    private val api: API,
    private val mapperUrls: UrlResponseToUrlAddress
): UrlDataSource {

    override suspend fun getUrls(): Results<UrlAddress> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}commons/service/config")
        return api.getUrls(
            msisdn = Application().myApplication?.userProfileAux?.userMsisdn?.toLong(),
            language = Application().myApplication?.userProfileAux?.lang.toString()
        ).create(mapperUrls)
    }
}