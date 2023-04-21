package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PrizesResponse(
//    val rewardList: kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType.Object,
//    val popupInfo: kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType.Object,
    val newMessages: Boolean,
    val userChangedCity: Boolean,
    @SerializedName("errorTO")
    val erro: ErrorTo,
    val availableRewards: List<AvailableReward> = listOf(),
    val historyRewards: List<HistoryReward> = listOf()
)

data class AvailableReward(
    val name: String,
    val descr: String,
    val additionalProperties: AdditionalProperties,
    val id: Int,
    val type: String,
    val cardLayout: CardLayout,
    val prizeTokenId: Int,
    val redeemed: Boolean,
    val expired: Boolean,
    val nearExpiration: Boolean,
    val attributionDate: Long,
    val expireDate: Long,
    val redeemDate: String,
    val status: String,
): Serializable

data class HistoryReward(
    val name: String,
    val description: String,
    val additionalProperties: AdditionalProperties,
    val id: Int,
    val type: String,
    val iconUrl: String,
    val prizeTokenId: Int,
    val redeemed: Boolean,
    val expired: Boolean,
    val nearExpiration: Boolean,
    val attributionDate: Long,
    val expireDate: Long,
    val redeemDate: Long,
    val status: String,
    val day: Long,
    val cardLayout: CardLayout
): Serializable

data class AdditionalProperties(
    val prizeCardId: Int,
    val prizeType: Int,
    val prizeIcon: String,
    val prizeTelcoCode: String,
    val prizeSyncDelivery: Int,
): Serializable

data class CardLayout(
    val historyPrizeTitle: String,
    val availablePrizeTitle: String,
    val name: String,
    val redeemedPrizeIconUrl: String,
    val genericPrizeIconUrl: String,
    val id: Int,
    val historyPrizeDescription: String,
    val iconUrl: String,
    val historyPrizeIconUrl: String,
): Serializable