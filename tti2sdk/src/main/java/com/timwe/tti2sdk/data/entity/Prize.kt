package com.timwe.tti2sdk.data.entity

import com.google.gson.Gson
import com.timwe.tti2sdk.data.model.response.AvailableReward
import com.timwe.tti2sdk.data.model.response.HistoryReward
import java.io.Serializable

data class Prize(
    val availableRewards: List<AvailableReward> = listOf(),
    val historyRewards: List<HistoryReward> = listOf()
): Serializable{

    fun clone(): Prize{
        val prize = Gson().toJson(this, Prize::class.java)
        return Gson().fromJson(prize, Prize::class.java)
    }

}
