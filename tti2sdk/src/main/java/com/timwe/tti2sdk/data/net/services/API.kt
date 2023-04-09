package com.timwe.tti2sdk.data.net.services

import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface API {

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*",
        "serviceId: 162392",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en"
    )
    @GET("avatars/customizations?")
    suspend fun getAvatarCustomizations(
        @Query("random") random: Boolean = false
    ): Response<AvatarResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*",
        "serviceId: 162392",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en")
    @POST("users/upsert")
    suspend fun postCreatOrUpdateUser(
        @Body  userAvatar: RequestCreateOrUpdateUser,
    ): Response<UserCreateAvatarResponse>


    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("missions/list?")
    suspend fun getMissions(
        @Header("serviceId") serviceId: Int,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("region") region: String
    ): Response<MissionGroupsResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*",
        "serviceId: 162392",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en")
    @POST("missions/start")
    suspend fun startMissions(

    ): Response<AckResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*",
        "serviceId: 162392",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en")
    @POST("missions/skip")
    suspend fun skipMissions(

    ): Response<AckResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*",
        "serviceId: 162392",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en")
    @POST("missions/redeem")
    suspend fun redeemMissions(

    ): Response<AckResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*",
        "serviceId: 162392",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en")
    @POST("missions/complete")
    suspend fun completeMissions(

    ): Response<AckResponse>

    @Headers(

    )
    @POST("missions/urls")
    suspend fun getUrls(

    ): Response<UrlResponse>
}

/*
        "serviceId: 178132",
        "msisdn: 9562ac77b5ff5fb7567265dc13a55e9d57959f18c8047a4bc1d490d4b311c12c",
        "lang: en",
        "tier: Gold",
        "region: abc"
 */