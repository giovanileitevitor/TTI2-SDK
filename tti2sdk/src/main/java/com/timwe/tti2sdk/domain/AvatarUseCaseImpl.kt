package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSource

class AvatarUseCaseImpl(
    private val avatarDataSource: AvatarDataSource
) : AvatarUseCase {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        return avatarDataSource.getAvatar(random)
    }

    override suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar> {
        return avatarDataSource.postCreatOrUpdateUser(userAvatar)
    }

}