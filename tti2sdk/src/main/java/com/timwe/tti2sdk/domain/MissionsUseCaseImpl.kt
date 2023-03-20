package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.remote.MissionsDataSource

class MissionsUseCaseImpl(
        private val missionsDataSource: MissionsDataSource
) : MissionsUseCase {

        override suspend fun getMissions(): Results<Mission>{
                return missionsDataSource.getMissions()
        }

//        override suspend fun updateMission(missionId: Int): Results<Boolean> {
//                return missionsDataSource.updateMission(missionId = missionId)
//        }
}