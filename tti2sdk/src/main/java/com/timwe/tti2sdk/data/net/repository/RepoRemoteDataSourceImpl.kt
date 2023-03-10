package com.timwe.tti2sdk.data.net.repository

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
    private val mapperAvatarResponseToAvatar: AvatarResponseToAvatar,
    private val mapperUserCreateAvatarResponseToUserAndAvatar: UserCreateAvatarResponseToUserAndAvatar,
) : RepoRemoteDataSource {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        return api.getAvatarCustomizations(random).create(mapperAvatarResponseToAvatar)
    }

    override suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar> {
        return api.postCreatOrUpdateUser(userAvatar).create(mapperUserCreateAvatarResponseToUserAndAvatar)
    }

}