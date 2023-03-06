package com.timwe.tti2sdk.net.repository

import com.timwe.tti2sdk.entity.Example
import com.timwe.tti2sdk.net.api.Results


class RepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override suspend fun getReposExample(q: String): Results<Example> {
        return repoRemoteDataSource.getReposExample(q = q)
    }

}