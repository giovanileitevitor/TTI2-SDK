package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.utils.Utils
import retrofit2.http.Header

class AvatarDataSourceImpl(
    private val api: API,
    private val mapperAvatar: AvatarResponseToAvatar,
    private val mapperUserCreateAvatar: UserCreateAvatarResponseToUserAndAvatar,
    private val context: Context
): AvatarDataSource {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}avatar/customizations")
        return api.getAvatarCustomizations(
                msisdn =  (context as Application).getUserProfile().userMsisdn!!.toLong(),
                lang =  (context as Application).getUserProfile().lang!!,
                tier =   (context as Application).getUserProfile().tier!!,
                random = random
        ).create(mapperAvatar)
    }

    override suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}users/upsert")
        return api.postCreatOrUpdateUser(
            msisdn =  (context as Application).getUserProfile()?.userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile()?.lang!!,
            userAvatar = userAvatar
        ).create(mapperUserCreateAvatar)
    }

}