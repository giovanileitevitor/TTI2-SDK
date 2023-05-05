package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.entity.Skip
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.remote.MissionsDataSource

class MissionsUseCaseImpl(
        private val missionsDataSource: MissionsDataSource
) : MissionsUseCase {

        override suspend fun getMissions(): Results<MissionGroups>{
                return missionsDataSource.getMissions()
        }

        override suspend fun startMissions(): Results<Ack> {
                return missionsDataSource.startMissions()
        }

        override suspend fun skipMissions(groupId: Long?): Results<Skip> {
                return missionsDataSource.skipMissions(groupId = groupId)
        }

        override suspend fun redeemMissions(): Results<Ack> {
                return missionsDataSource.redeemMissions()
        }

        override suspend fun completeMissions(): Results<Ack> {
               return missionsDataSource.completeMissions()
        }

}