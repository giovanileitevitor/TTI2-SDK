package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.entity.Skip
import com.timwe.tti2sdk.data.model.request.GroupIdRequest
import com.timwe.tti2sdk.data.model.request.GroupMissionIdRequest
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.mapper.MissionResponseToMission
import com.timwe.tti2sdk.data.net.mapper.SkipResponseToSkip
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.MyApplication
import com.timwe.utils.Utils

class MissionsDataSourceImpl(
    private val api: API,
    private val mapperMission: MissionResponseToMission,
    private val mapperAck: AckResponseToAck,
    private val mapperSkipResponse: SkipResponseToSkip
): MissionsDataSource {

    override suspend fun getMissions(): Results<MissionGroups> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/list")
        return api.getMissions(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
        ).create(mapperMission)
    }

    override suspend fun startMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/start")
        return api.startMissions(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
        ).create(mapperAck)
    }

    override suspend fun skipMissions(groupId: Long?): Results<Skip>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/skip")
        return api.skipMissions(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
            groupIdRequest = GroupIdRequest(groupId = groupId ?: GROUP_ID_ADVENTURES)
        ).create(mapperSkipResponse)
    }

    override suspend fun redeemMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/redeem")
        return api.redeemMissions(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
        ).create(mapperAck)
    }

    override suspend fun completeMissions(groupMissionId: Long): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/complete")
        return api.completeMissions(
            msisdn =  MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang =  MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier =  MyApplication.instance?.userProfile?.tier!!,
            groupMissionId = GroupMissionIdRequest(groupMissionId = groupMissionId)
        ).create(mapperAck)
    }

    companion object{
        private const val GROUP_ID_ADVENTURES = 202L
    }

}