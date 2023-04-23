package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
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
    private val mapperUrls: UrlResponseToUrlAddress,
    private val context: Context
): UrlDataSource {

    override suspend fun getUrls(msisdn: Long, lang: String): Results<UrlAddress> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}commons/service/config")
        return api.getUrls(
            msisdn =  (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile().lang!!,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile().tier!!,
        ).create(mapperUrls)
    }
}