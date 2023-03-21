package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.data.model.response.Options

data class CombinedResultForRecyclerView(
    val positionSelected: Int,
    val riveInputGender: String,
    val riveInputKey: String,
    val listOptions: List<Options>,
    val firstLoad: Boolean? = true
)