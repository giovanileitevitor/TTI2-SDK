package com.timwe.tti2sdk.data.net.repository

import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.model.request.RequestSaveAvatar
import com.timwe.tti2sdk.data.net.api.Results

class RepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        return repoRemoteDataSource.getAvatar(random)
    }

    override suspend fun postSaveAvatar(userAvatar: RequestSaveAvatar) {
        repoRemoteDataSource.postSaveAvatar(userAvatar)
    }

}