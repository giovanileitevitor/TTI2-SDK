package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.model.response.PrizesResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class PrizesResponseToPrize : Mapper<PrizesResponse, PrizeFlow>(){

    override fun transform(item: PrizesResponse): PrizeFlow {
        return PrizeFlow(
            availableRewards = item.availableRewards,
            historyRewards = item.historyRewards
        )
    }

}