package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class RedeemPrizeResponse(
    @SerializedName("errorTO")
    val erro: ErrorTo,
)
