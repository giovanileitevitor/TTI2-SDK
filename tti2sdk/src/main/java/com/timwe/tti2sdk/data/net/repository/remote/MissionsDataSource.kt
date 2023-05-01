package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.entity.MissionGroup
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.net.api.Results

interface MissionsDataSource {

    suspend fun getDailyMissions(): Results<List<Mission>>

    suspend fun getMissions(): Results<MissionGroups>

    suspend fun startMissions(): Results<Ack>

    suspend fun skipMissions(): Results<Ack>

    suspend fun redeemMissions(): Results<Ack>

    suspend fun completeMissions(): Results<Ack>

}