package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.net.api.Results

interface MissionsUseCase{

    suspend fun getMissions(): Results<MissionGroups>

    suspend fun startMissions(): Results<Ack>

    suspend fun skipMissions(): Results<Ack>

    suspend fun redeemMissions(): Results<Ack>

    suspend fun completeMissions(): Results<Ack>

}