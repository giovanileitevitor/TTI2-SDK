package com.timwe.tti2sdk.data.net.repository

import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.model.request.RequestSaveAvatar
import com.timwe.tti2sdk.data.net.api.Results

interface RepoRemoteDataSource {

    suspend fun getAvatar(random: Boolean): Results<Avatar>

    suspend fun postSaveAvatar(userAvatar: RequestSaveAvatar)

}