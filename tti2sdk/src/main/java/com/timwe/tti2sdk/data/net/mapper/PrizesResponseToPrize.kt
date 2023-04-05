package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.data.model.response.PrizesResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class PrizesResponseToPrize : Mapper<PrizesResponse, Prize>(){

    override fun transform(item: PrizesResponse): Prize {
        return Prize(
            availableRewards = item.availableRewards,
            historyRewards = item.historyRewards
        )
    }

}