package com.timwe.tti2sdk.net.repository

import com.timwe.tti2sdk.entity.Example
import com.timwe.tti2sdk.net.api.Results
import com.timwe.tti2sdk.net.data.create
import com.timwe.tti2sdk.net.mapper.ExampleResponseToExample
import com.timwe.tti2sdk.net.services.API

class RepoRemoteDataSourceImpl(
    private val api: API,
    private val mapperExampleResponseToExample: ExampleResponseToExample,
) : RepoRemoteDataSource {

    override suspend fun getReposExample(q: String): Results<Example> {
        return api.getReposExample(q).create(mapperExampleResponseToExample)
    }

}