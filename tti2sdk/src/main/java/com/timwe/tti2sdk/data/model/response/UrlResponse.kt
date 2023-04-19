package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class UrlResponse(
    @SerializedName("errorTO")
    val erro: ErrorTo,

    @SerializedName("newMessages")
    val newMessages: Boolean,

    @SerializedName("userChangedCity")
    val userChangedCity: Boolean,

    @SerializedName("token")
    val token: String,

    @SerializedName("userRegistered")
    val userRegistered: Boolean?,

    @SerializedName("startDate")
    val startDate: Long,

    @SerializedName("phase")
    val phase: Int,

    @SerializedName("additionalInfo")
    val additionalInfo: AdditionalInfoUrl,

)

data class AdditionalInfoUrl(
    val moveNextStage: String,
    val howToWin: String,
    val learnMore: String,
    val finalPrize: String,
    val rulesPrizes: String,
    val termsAndConditions: String,
)