package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.model.response.MissionGroupsResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class MissionResponseToDailyMission: Mapper<MissionGroupsResponse, List<Mission>>() {

    override fun transform(item: MissionGroupsResponse): List<Mission> {
        val missions = arrayListOf<Mission>()

        item.missionGroups.forEach {
            if(it.name == DAILY){
                it.missions.forEach { mission ->
                    missions.add(
                        Mission(
                            id = mission.missionId,
                            type = mission.missionType,
                            flagColor = "",
                            flagText = "Daily",
                            extraFlagColor = "",
                            extraFlagText = "",
                            distanceUnit = "km",
                            distance = 0,
                            title = mission.name,
                            subtitle = mission.description,
                            qtdItens = mission.rewards.size.toString() ?: "0",
                            extraInfo = "",
                            status = mission.status
                        )
                    )
                }
            }else if(it.name == ADVENTURE){

            }else if(it.name == BOOSTER){

            }else{

            }
        }

        return missions

    }

    companion object{
        private const val DAILY = "Daily"
        private const val ADVENTURE = "Adventure"
        private const val BOOSTER = "Booster"
    }
}