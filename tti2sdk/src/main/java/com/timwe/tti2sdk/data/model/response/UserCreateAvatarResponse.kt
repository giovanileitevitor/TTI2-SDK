package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class UserCreateAvatarResponse(
    @SerializedName("errorTO")
    val erro: ErrorTo,

    @SerializedName("profile")
    val profile: ProfileUser,

    @SerializedName("newMessages")
    val newMessages: Boolean,

    @SerializedName("userChangedCity")
    val userChangedCity: Boolean,

    @SerializedName("missionGroups")
    val listMissionGroups: List<MissionGroup>
)

data class MissionGroup(
    val currentValue: Int,
    val description: String,
    val groupId: Int,
    val groupType: String,
    val imageUrl: String,
    val name: String,
    val shortDescription: String,
    val order: Int,
    val threshold: Int,
    @SerializedName("missions")
    val listMissions: List<Mission>
)

data class Mission(
    val missionId: Int,
    val missionType: String,
    val missionSubType: String,
    val groupMissionId: Int,
    val name: String,
    val type: String,
    val description: String,
    val category: String,
    val imageUrl: String,
    val status: String,
    val currentValue: Int,
    val threshold: String,
    val eventType: String,
    val eventValue: String,
    val quiz: String,
    val educationalCards: String,
    val skipAllowed: Boolean,

    @SerializedName("additionalProperties")
    val additionalProperties: AdditionalPropertiesAvatar,

    @SerializedName("rewards")
    val rewardsMission: List<RewardsMission>
)

data class RewardsMission(
    val description: String,
    val imageUrl: String,
    val label: String,
    val prizeName: String,
    val prizeValue: String,
    val value: String,
)

data class AdditionalPropertiesAvatar(
    @SerializedName("mission_menu_description")
    val missionDescription: String,
    @SerializedName("mission_menu_title")
    val missionTitle: String
)

data class ErrorTo(
    @SerializedName("code")
    val code: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("error")
    val error: Boolean,
)

data class ProfileUser(
    @SerializedName("username")
    val username: String,

    @SerializedName("plateNumber")
    val plateNumber: String,

    @SerializedName("userAvatar")
    val userAvatar: UserAvatarResponse,
)