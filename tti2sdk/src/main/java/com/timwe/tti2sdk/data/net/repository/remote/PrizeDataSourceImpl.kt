package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.PrizesResponseToPrize
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.utils.Utils

class PrizeDataSourceImpl(
    private val api: API,
    private val mapperPrizesResponseToPrize: PrizesResponseToPrize,
    private val context: Context
): PrizeDataSource {

    override suspend fun getPrize(): Results<PrizeFlow> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/rewards")
        return api.getPrizes(
            msisdn =  (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile().lang!!,
            tier =  (context as Application).getUserProfile().tier!!,
        ).create(mapperPrizesResponseToPrize)
    }

}