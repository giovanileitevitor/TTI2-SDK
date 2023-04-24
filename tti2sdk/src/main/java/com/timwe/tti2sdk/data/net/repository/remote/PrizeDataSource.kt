package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.entity.RedeemPrize
import com.timwe.tti2sdk.data.model.request.RequestReddemPrize
import com.timwe.tti2sdk.data.net.api.Results

interface PrizeDataSource {

    suspend fun getPrize(): Results<PrizeFlow>

    suspend fun postRedeemPrizes(requestReddemPrize: RequestReddemPrize): Results<RedeemPrize>

}