package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.net.api.Results

interface MissionsDataSource {

    suspend fun getMissions(): Results<Mission>

    //suspend fun updateMission(missionId: Int): Results<Boolean>

}