package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AckResponse(
    @SerializedName("statusCode")
    val statusCode: Int? = 0
): Serializable
