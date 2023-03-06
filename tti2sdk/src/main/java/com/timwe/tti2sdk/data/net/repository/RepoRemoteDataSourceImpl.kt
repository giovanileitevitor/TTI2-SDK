package com.timwe.tti2sdk.data.net.repository

import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.model.request.RequestSaveAvatar
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AvatarResponseToAvatar
import com.timwe.tti2sdk.data.net.services.API

class RepoRemoteDataSourceImpl(
    private val api: API,
    private val mapperAvatarResponseToAvatar: AvatarResponseToAvatar,
) : RepoRemoteDataSource {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        return api.getAvatarCustomizations(random).create(mapperAvatarResponseToAvatar)
    }

    override suspend fun postSaveAvatar(userAvatar: RequestSaveAvatar) {
        api.postSaveAvatars(userAvatar)
    }

}