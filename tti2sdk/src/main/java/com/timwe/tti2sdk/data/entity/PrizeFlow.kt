package com.timwe.tti2sdk.data.entity

import com.google.gson.Gson
import com.timwe.tti2sdk.data.model.response.AvailableReward
import com.timwe.tti2sdk.data.model.response.HistoryReward
import java.io.Serializable

data class PrizeFlow(
    val availableRewards: List<AvailableReward> = listOf(),
    val historyRewards: List<HistoryReward> = listOf()
): Serializable {

    fun clone(): PrizeFlow{
        val prizeFlow = Gson().toJson(this, PrizeFlow::class.java)
        return Gson().fromJson(prizeFlow, PrizeFlow::class.java)
    }

}
