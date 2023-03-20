package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.net.api.Results

interface MissionsUseCase{

    suspend fun getMissions(): Results<List<Mission>>

    suspend fun updateMission(missionId: Int)

}