package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListCityResponse(
    @SerializedName("errorTO")
    val erro: ErrorTo,
    val newMessages: Boolean,
    val userChangedCity: Boolean,
    val cityList: List<City>
): Serializable
