package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.entity.RedeemPrize
import com.timwe.tti2sdk.data.model.request.RequestReddemPrize
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.remote.PrizeDataSource

class PrizeUseCaseImpl(
    private val prizeDataSource: PrizeDataSource
): PrizeUseCase {

    override suspend fun getPrizes(): Results<PrizeFlow> {
        return prizeDataSource.getPrize()
    }

    override suspend fun postRedeemPrizes(requestReddemPrize: RequestReddemPrize): Results<RedeemPrize> {
        return prizeDataSource.postRedeemPrizes(requestReddemPrize)
    }

}