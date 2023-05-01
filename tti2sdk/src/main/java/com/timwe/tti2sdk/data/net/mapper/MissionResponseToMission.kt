package com.timwe.tti2sdk.data.net.mapper

import android.util.Log
import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.MissionGroup
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.model.response.MissionGroupsResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class MissionResponseToMission: Mapper<MissionGroupsResponse, MissionGroups>(){

    override fun transform(item: MissionGroupsResponse): MissionGroups {

        val avatar = Gson().toJson(item, MissionGroupsResponse::class.java)
        Log.i("json", avatar)

        return MissionGroups(
            newMessages = item.newMessages,
            userChangedCity = item.userChangedCity,
            missionGroup = item.missionGroups
        )

    }

}