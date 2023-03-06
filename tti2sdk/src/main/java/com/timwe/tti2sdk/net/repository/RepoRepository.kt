package com.timwe.tti2sdk.net.repository

import com.timwe.tti2sdk.entity.Example
import com.timwe.tti2sdk.net.api.Results

interface RepoRepository {

    suspend fun getReposExample(q: String): Results<Example>

}