package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MissionResponse(
    @SerializedName("missionId")
    val missionId: Int
) : Serializable