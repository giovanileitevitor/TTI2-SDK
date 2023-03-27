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

data class MissionGroups(
    val newMessages: Boolean? = false,
    val userChangedCity: Boolean? = false,
    val missionGroup: List<MissionGroup>
)

data class MissionGroup(
    val groupID: Long,
    val name: String,
    val imageURL: Any? = null,
    val description: String,
    val shortDescription: Any? = null,
    val groupType: String,
    val missions: List<Mission2>,
    val additionalProperties: MissionGroupAdditionalProperties
)

data class Mission2 (
    val missionID: Long,
    val groupMissionID: Long,
    val name: String,
    val description: String,
    val category: String,
    val imageURL: String,
    val status: String,
    val rewards: List<Reward>,
    val currentValue: Long,
    val threshold: Long,
    val additionalProperties: MissionAdditionalProperties,
    val skipAllowed: Boolean
)

data class MissionAdditionalProperties (
    val missionMenuTitle: String? = null,
    val missionMenuDescription: String? = null,
    val missionActionUrl2: String? = null,
    val missionActionUrl1: String? = null,
    val missionAction: String? = null,
    val missionButtonKey: String? = null,
    val missionButtonText: String? = null,
    val missionButtonScope: String? = null
)

data class Reward (
    val prizeName: String,
    val description: String,
    val value: Long,
    val imageURL: Any? = null,
    val label: Any? = null
)

data class MissionGroupAdditionalProperties(
    val id: Int? = 0
)