package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.model.response.MissionGroupsResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class MissionResponseToMission: Mapper<MissionGroupsResponse, MissionGroups>(){

    override fun transform(item: MissionGroupsResponse): MissionGroups {

        return MissionGroups(
            newMessages = item.newMessages,
            userChangedCity = item.userChangedCity,
            missionGroup = emptyList()
        )

    }

}