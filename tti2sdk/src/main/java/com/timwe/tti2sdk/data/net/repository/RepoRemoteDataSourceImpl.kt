package com.timwe.tti2sdk.data.net.repository

import android.util.Log
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.mapper.UserCreateAvatarResponseToUserAndAvatar
import com.timwe.tti2sdk.data.net.services.API

class RepoRemoteDataSourceImpl(
    private val api: API,
    private val mapperAvatar: AvatarResponseToAvatar,
    private val mapperUserCreateAvatar: UserCreateAvatarResponseToUserAndAvatar
): RepoRemoteDataSource {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        Log.i("SDK", "Request: ${BuildConfig.BASE_URL}avatar/customizations")
        return api.getAvatarCustomizations(
            random = random
        ).create(mapperAvatar)
    }

    override suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar> {
        Log.i("SDK", "Request: ${BuildConfig.BASE_URL}users/upsert")
        return api.postCreatOrUpdateUser(
            userAvatar =  userAvatar
        ).create(mapperUserCreateAvatar)
    }

}