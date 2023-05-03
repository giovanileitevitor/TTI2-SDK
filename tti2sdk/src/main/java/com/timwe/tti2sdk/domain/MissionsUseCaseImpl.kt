package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.AdventureMissions
import com.timwe.tti2sdk.data.entity.BoosterMissions
import com.timwe.tti2sdk.data.entity.DailyMissions
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.entity.MissionGroup
import com.timwe.tti2sdk.data.entity.MissionGroups
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

        override suspend fun skipMissions(): Results<Ack> {
                return missionsDataSource.skipMissions()
        }

        override suspend fun redeemMissions(): Results<Ack> {
                return missionsDataSource.redeemMissions()
        }

        override suspend fun completeMissions(): Results<Ack> {
               return missionsDataSource.completeMissions()
        }

}