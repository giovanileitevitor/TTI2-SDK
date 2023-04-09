package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class UrlResponse(
    @SerializedName("id")
    val id : Int,

    @SerializedName("urls")
    val urls: List<String>
)
