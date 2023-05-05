package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.MissionGroups
import com.timwe.tti2sdk.data.entity.Skip
import com.timwe.tti2sdk.data.model.request.GroupIdRequest
import com.timwe.tti2sdk.data.model.response.SkipResponse
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.mapper.MissionResponseToMission
import com.timwe.tti2sdk.data.net.mapper.SkipResponseToSkip
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.utils.Utils

class MissionsDataSourceImpl(
    private val api: API,
    private val mapperMission: MissionResponseToMission,
    private val mapperAck: AckResponseToAck,
    private val mapperSkipResponse: SkipResponseToSkip,
    private val context: Context,
): MissionsDataSource {

    override suspend fun getMissions(): Results<MissionGroups> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/list")
        return api.getMissions(
            msisdn =  (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile().lang!!,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile().tier!!,
        ).create(mapperMission)
    }

    override suspend fun startMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/start")
        return api.startMissions(
            msisdn =  (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile().lang!!,
            tier =  (context as Application).getUserProfile().tier!!,
        ).create(mapperAck)
    }

    override suspend fun skipMissions(groupId: Long?): Results<Skip>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/skip")
        return api.skipMissions(
            msisdn =  (context as Application).getUserProfile()?.userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile()?.lang!!,
            tier =  (context as Application).getUserProfile()?.tier!!,
            groupIdRequest = GroupIdRequest(groupId = groupId ?: GROUP_ID_ADVENTURES)
        ).create(mapperSkipResponse)
    }

    override suspend fun redeemMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/redeem")
        return api.redeemMissions(
            msisdn =  (context as Application).getUserProfile()?.userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile()?.lang!!,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile()?.tier!!,
        ).create(mapperAck)
    }

    override suspend fun completeMissions(): Results<Ack>{
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/complete")
        return api.completeMissions(
            msisdn =  (context as Application).getUserProfile()?.userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile()?.lang!!,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile()?.tier!!,
        ).create(mapperAck)
    }

    companion object{
        private const val GROUP_ID_ADVENTURES = 202L
    }

}