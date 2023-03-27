package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import com.timwe.tti2sdk.data.entity.Group
import com.timwe.tti2sdk.data.entity.MissionGroup
import java.io.Serializable

class MissionResponse(
    @SerializedName("missionId")
    val missionId: Int
) : Serializable

class MissionGroupsResponse(
    @SerializedName("newMessages")
    val newMessages: Boolean,

    @SerializedName("userChangedCity")
    val userChangedCity: Boolean,

    @SerializedName("missionGroups")
    val missionGroups: List<MissionGroup>? = emptyList(),

    @SerializedName("groups")
    val groups: Map<String, Group>

)