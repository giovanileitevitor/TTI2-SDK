package com.timwe.tti2sdk.net.services

import com.timwe.tti2sdk.model.response.ExampleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("search/repositories")
    suspend fun getReposExample(
        @Query("q") q: String,
    ): Response<ExampleResponse>

}