package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.ProfileInfo
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.request.RequestReedenMission
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.InfosProfileHomeResponseToProfileInfo
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.MyApplication
import com.timwe.utils.Utils

class AvatarDataSourceImpl(
    private val api: API,
    private val mapperAvatar: AvatarResponseToAvatar,
    private val mapperUserCreateAvatar: UserCreateAvatarResponseToUserAndAvatar,
    private val mapperAck: AckResponseToAck,
    private val mapperProfileInfos: InfosProfileHomeResponseToProfileInfo
): AvatarDataSource {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}avatar/customizations")
        return api.getAvatarCustomizations(
                msisdn = MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
                lang = MyApplication.instance?.userProfile?.lang!!,
                plan = MyApplication.instance?.userProfile?.plan!!,
                tier = MyApplication.instance?.userProfile?.tier!!,
                random = random
        ).create(mapperAvatar)
    }

    override suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/upsert")
        return api.postCreatOrUpdateUser(
            msisdn = MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang = MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            userAvatar = userAvatar
        ).create(mapperUserCreateAvatar)
    }

    override suspend fun saveMissionCompleteAvatar(requestReedenMission: RequestReedenMission): Results<Ack> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}missions/redeem")
        return api.redeemMissions(
            msisdn = MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang = MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier = MyApplication.instance?.userProfile?.tier!!,
            requestReedenMission = requestReedenMission
        ).create(mapperAck)
    }

    override suspend fun getProfileInfos(): Results<ProfileInfo> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/home")
        return api.getInfosProfileHome(
            msisdn = MyApplication.instance?.userProfile?.userMsisdn!!.toLong(),
            lang = MyApplication.instance?.userProfile?.lang!!,
            plan = MyApplication.instance?.userProfile?.plan!!,
            tier = MyApplication.instance?.userProfile?.tier!!,
        ).create(mapperProfileInfos)
    }

}