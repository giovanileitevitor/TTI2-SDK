package com.timwe.tti2sdk.data.model.response

import java.io.Serializable

data class CityInfoResponse(
    val id: Int = 0,
    val popups: Any? = null,
    val errorTo: Any? = null,
    val newMessages: Boolean,
    val userChangedCity: Boolean,
    val city: City
): Serializable

data class City(
    val id: Long,
    val name: String,
    val startKm: Long,
    val capital: Boolean,
    val shareImageUrl: String,
    val cardImageUrl: List<String>? = null,
    val trivia: String,
    val wikipedia: Wikipedia? = null,
    val about: Any? = null,
    val background: Any? = null,
    val sticker: String,
    val stickerList: Any? = null,
    val rewardList: List<RewardList>,
    val aroundHere: List<AroundHere>,
    val websites: Any? = null,
    val news: Any? = null,
    val videos: Any? = null,
    val order: Long,
    val xpos: String,
    val ypos: String
): Serializable

data class AroundHere (
    val category: Category,
    val destinations: List<Wikipedia>
): Serializable

data class Category (
    val id: Long,
    val order: Long,
    val name: String,
    val icon: String
): Serializable

data class Wikipedia (
    val id: Long,
    val name: String,
    val category: String,
    val imageUrl: String,
    val redirectUrl: String
): Serializable

data class RewardList (
    val name: String,
    val description: String? = null,
    val additionalProperties: AdditionalPropertiesCity,
    val id: Long,
    val type: String,
    val iconUrl: String,
    val cardLayout: CardLayoutCity
): Serializable

data class AdditionalPropertiesCity (
    val prizeAssetId: Long? = null,
    val prizeCardId: Long,
    val prizeType: Long,
    val prizeIcon: String,
    val prizeTelcoCode: String? = null,
    val prizeSyncDelivery: Long? = null
): Serializable

data class CardLayoutCity (
    val availablePrizeDescription: Any? = null,
    val availablePrizeTitle: String? = null,
    val name: String,
    val description: String? = null,
    val genericPrizeIconURL: String? = null,
    val id: Long,
    val iconURL: String,
    val historyPrizeDescription: String? = null,
    val historyPrizeTitle: String? = null
): Serializable
