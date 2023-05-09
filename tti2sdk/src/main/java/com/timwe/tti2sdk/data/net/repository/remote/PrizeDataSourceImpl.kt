package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.entity.RedeemPrize
import com.timwe.tti2sdk.data.model.request.RequestReddemPrize
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.PrizesResponseToPrize
import com.timwe.tti2sdk.data.net.mapper.RedeemPrizeResponseToRedeemPrize
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.MyApplication
import com.timwe.utils.Utils

class PrizeDataSourceImpl(
    private val api: API,
    private val mapperPrizesResponseToPrize: PrizesResponseToPrize,
    private val mapperRedeemPrizeResponseToRedeemPrize: RedeemPrizeResponseToRedeemPrize
): PrizeDataSource {

    override suspend fun getPrize(): Results<PrizeFlow> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/rewards")
        return api.getPrizes(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
        ).create(mapperPrizesResponseToPrize)
    }

    override suspend fun postRedeemPrizes(requestReddemPrize: RequestReddemPrize): Results<RedeemPrize> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}prize/redeem")
        return api.getRedeemPrize(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
            requestReddemPrize = requestReddemPrize
        ).create(mapperRedeemPrizeResponseToRedeemPrize)
    }

}