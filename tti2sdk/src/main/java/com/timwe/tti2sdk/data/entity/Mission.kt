package com.timwe.tti2sdk.data.entity

data class Mission(
    var id: Int,
    var type: String,
    var flagColor: String,
    var distance: Int,
    var distanceUnit: String,
    var title: String,
    var subtitle: String,
    var extraInfo: String
)
