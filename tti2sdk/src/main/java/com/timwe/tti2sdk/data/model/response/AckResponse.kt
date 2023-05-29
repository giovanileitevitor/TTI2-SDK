package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AckResponse(
    @SerializedName("statusCode")
    val statusCode: Int? = 0
): Serializable

data class SkipResponse(
    @SerializedName("skipped")
    val skipped: Boolean,

    @SerializedName("status")
    val status: String
): Serializable