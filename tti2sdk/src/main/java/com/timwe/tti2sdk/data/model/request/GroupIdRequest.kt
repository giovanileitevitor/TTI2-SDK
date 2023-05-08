package com.timwe.tti2sdk.data.model.request

import com.google.gson.annotations.SerializedName

data class GroupIdRequest(
    @SerializedName("groupId")
    var groupId: Long
)

data class GroupMissionIdRequest(
    @SerializedName("groupMissionId")
    var groupMissionId: Long
)
