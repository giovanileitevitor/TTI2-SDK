package com.timwe.tti2sdk.data.net.services

import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.response.AvatarResponse
import com.timwe.tti2sdk.data.model.response.UserCreateAvatarResponse
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

}