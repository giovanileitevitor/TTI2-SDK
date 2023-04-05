package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.PrizesResponseToPrize
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.utils.Utils

class PrizeDataSourceImpl(
    private val api: API,
    private val mapperPrizesResponseToPrize: PrizesResponseToPrize
): PrizeDataSource {

    override suspend fun getPrize(): Results<Prize> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/rewards")
        return api.getPrizes().create(mapperPrizesResponseToPrize)
    }

}