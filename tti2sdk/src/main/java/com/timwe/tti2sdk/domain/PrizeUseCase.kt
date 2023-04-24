package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.entity.RedeemPrize
import com.timwe.tti2sdk.data.model.request.RequestReddemPrize
import com.timwe.tti2sdk.data.net.api.Results

interface PrizeUseCase {

    suspend fun getPrizes(): Results<PrizeFlow>

    suspend fun postRedeemPrizes(requestReddemPrize: RequestReddemPrize): Results<RedeemPrize>

}