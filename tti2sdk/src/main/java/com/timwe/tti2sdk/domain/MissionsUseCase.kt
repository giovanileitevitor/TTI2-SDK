package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.entity.Skip
import com.timwe.tti2sdk.data.net.api.Results

interface MissionsUseCase{

    suspend fun getMissions(): Results<MissionGroups>

    suspend fun startMissions(): Results<Ack>

    suspend fun skipMissions(groupId: Long?): Results<Skip>

    suspend fun redeemMissions(): Results<Ack>

    suspend fun completeMissions(groupMissionId: Long): Results<Ack>

}