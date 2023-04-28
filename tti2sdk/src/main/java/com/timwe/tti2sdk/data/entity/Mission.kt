package com.timwe.tti2sdk.data.entity

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Mission(
    var id: Long,
    var type: String,
    var flagColor: String,
    var flagText: String,
    var extraFlagColor: String,
    var extraFlagText: String,
    var distance: Long,
    var distanceUnit: String,
    var title: String,
    var subtitle: String,
    var extraInfo: String,
    var qtdItens: String,
    var status: String
)

data class MissionGroups(
    val newMessages: Boolean? = false,
    val userChangedCity: Boolean? = false,
    val missionGroup: List<MissionGroup>,
//    val groups: Map<String, Group>
)

data class MissionGroup(
    val groupId: Long,
    val name: String,
    val imageUrl: String? = null,
    val description: String,
    val shortDescription: String? = null,
    val groupType: String,
    val order: Int,
    @SerializedName("missions")
    val missions: List<Mission2>,
    val additionalProperties: GroupAdditionalProperties
)

typealias GroupAdditionalProperties = JsonObject

data class Group(
    val groupID: Long,
    val name: String,
    val imageURL: Any? = null,
    val description: String,
    val shortDescription: Any? = null,
    val groupType: String,
    val missions: List<Mission2>,
    val additionalProperties: GroupAdditionalProperties
)

data class Mission2 (
    val missionId: Long,
    val missionType: String,
    val groupMissionId: Long,
    val name: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val status: String,
    val rewards: List<Reward>,
    val currentValue: Long,
    val threshold: Long,
    val additionalProperties: MissionAdditionalProperties,
    val skipAllowed: Boolean
)

data class MissionAdditionalProperties (
    @SerializedName("mission_menu_title")
    val missionMenuTitle: String,
    @SerializedName("mission_type")
    val missionType: String,
    @SerializedName("mission_menu_description")
    val missionMenuDescription: String,
)

data class Reward (
    val prizeName: String,
    val description: String,
    val prizeValue: String,
    val value: Long,
    val imageUrl: String,
    val label: String
)

//    fun getMockedMissions(qtd: Int): ArrayList<Mission>{
//        val list = ArrayList<Mission>()
//        for(i in 1..qtd) {
//            list.add(
//                Mission(
//                    id = i,
//                    type = if(i%2 !=0) "hasActions" else "error",
//                    flagColor = "red",
//                    flagText = if(i%2 !=0) "Daily" else "Weekly",
//                    extraFlagColor = "gold",
//                    extraFlagText = "Actions needed",
//                    distance = if(i%2 !=0) 3 else 9,
//                    distanceUnit = if(i%2 !=0) "km" else "m",
//                    title = if(i%2 !=0) "Title 1" else "Title 2",
//                    subtitle = if(i%2 !=0) "12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas" else "12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas 12 Days of Christmas",
//                    extraInfo = if(i%2 !=0) "extra informations 1" else "extra informations 2",
//                    qtdItens = "2 of 5"
//                )
//            )
//        }
//        return list
//    }
