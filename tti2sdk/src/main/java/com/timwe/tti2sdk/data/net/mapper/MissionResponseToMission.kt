package com.timwe.tti2sdk.data.net.mapper

import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.model.response.MissionResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class MissionResponseToMission: Mapper<MissionResponse, Mission>(){

    override fun transform(item: MissionResponse): Mission {

        val aux = Mission(
            id = item.missionId,
            type = "empty",
            flagColor = "empty",
            flagText = "teste",
            extraFlagColor = "empty",
            extraFlagText = "teste extra flag",
            distance = 3,
            distanceUnit = "km",
            title = "title",
            subtitle = "subtitle",
            extraInfo = "extra info extra info extra info"
        )

        return aux
    }

}