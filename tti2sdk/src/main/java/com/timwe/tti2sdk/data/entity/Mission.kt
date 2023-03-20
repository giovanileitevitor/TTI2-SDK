package com.timwe.tti2sdk.data.entity

data class Mission(
    var id: Int,
    var type: String,
    var flagColor: String,
    var flagText: String,
    var extraFlagColor: String,
    var extraFlagText: String,
    var distance: Int,
    var distanceUnit: String,
    var title: String,
    var subtitle: String,
    var extraInfo: String
)


data class Missions(
    var missions: List<Mission>
)

/*
type: info, completed, error, hasActions

 */


