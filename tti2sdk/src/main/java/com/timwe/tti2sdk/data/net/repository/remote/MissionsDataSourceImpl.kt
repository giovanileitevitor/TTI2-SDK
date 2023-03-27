package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.entity.MissionGroup
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.mapper.MissionResponseToMission
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.utils.Utils

class MissionsDataSourceImpl(
    private val api: API,
    private val mapperMission: MissionResponseToMission,
    private val mapperAck: AckResponseToAck
): MissionsDataSource {

    override suspend fun getMissions(): Results<MissionGroups> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/list")
        return api.getMissions(

        ).create(mapperMission)
    }

    override suspend fun startMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/start")
        return api.startMissions(

        ).create(mapperAck)
    }

    override suspend fun skipMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/skip")
        return api.skipMissions(

        ).create(mapperAck)
    }

    override suspend fun redeemMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/redeem")
        return api.redeemMissions(

        ).create(mapperAck)
    }

    override suspend fun completeMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/complete")
        return api.completeMissions(

        ).create(mapperAck)
    }


}