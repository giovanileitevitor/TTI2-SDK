package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.RedeemPrize
import com.timwe.tti2sdk.data.model.response.RedeemPrizeResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class RedeemPrizeResponseToRedeemPrize: Mapper<RedeemPrizeResponse, RedeemPrize>() {

    override fun transform(item: RedeemPrizeResponse): RedeemPrize {
        return RedeemPrize()
    }

}