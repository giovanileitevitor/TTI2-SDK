package com.timwe.tti2sdk.data.net.repository.remote

import androidx.datastore.preferences.protobuf.Api
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.MissionResponseToMission
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.utils.Utils

class MissionsDataSourceImpl(
    private val api: API,
    private val mapperMission: MissionResponseToMission
    //private val mapperUpdate: AckResponseToAck
): MissionsDataSource {

    override suspend fun getMissions(): Results<Mission> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}mission/getMissions")
        return api.getMissions(

        ).create(mapperMission)
    }

//    override suspend fun updateMission(missionId: Int): Results<Boolean> {
//        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}mission/updateMission")
//        return api.updateMission(
//            missionId = missionId
//        ).create(mapperUpdate)
//
//    }

}