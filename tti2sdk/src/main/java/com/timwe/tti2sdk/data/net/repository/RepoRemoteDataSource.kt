package com.timwe.tti2sdk.data.net.repository

import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.net.api.Results

interface RepoRemoteDataSource {

    suspend fun getAvatar(random: Boolean): Results<Avatar>

    suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar>

}