package com.timwe.tti2sdk.data.net.services

import com.timwe.tti2sdk.data.model.request.EventReport
import com.timwe.tti2sdk.data.model.request.GroupIdRequest
import com.timwe.tti2sdk.data.model.request.GroupMissionIdRequest
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.request.RequestReddemPrize
import com.timwe.tti2sdk.data.model.request.RequestReedenMission
import com.timwe.tti2sdk.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface API {

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("avatars/customizations?")
    suspend fun getAvatarCustomizations(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
        @Query("random") random: Boolean = false
    ): Response<AvatarResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @POST("users/upsert")
    suspend fun postCreatOrUpdateUser(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Body  userAvatar: RequestCreateOrUpdateUser,
    ): Response<UserCreateAvatarResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("missions/list")
    suspend fun getMissions(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("plan") plan: String,
        @Header("region") region: String = "es"
    ): Response<MissionGroupsResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @POST("missions/start")
    suspend fun startMissions(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("region") region: String = "es",
    ): Response<AckResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @POST("missions/skip")
    suspend fun skipMissions(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("plan") plan: String = "prepaid",
        @Body groupIdRequest: GroupIdRequest
    ): Response<SkipResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @POST("missions/redeem")
    suspend fun redeemMissions(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("plan") plan: String,
    ): Response<AckResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @POST("missions/redeem")
    suspend fun redeemMissions(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("plan") plan: String,
        @Body requestReedenMission: RequestReedenMission
    ): Response<AckResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @POST("missions/complete")
    suspend fun completeMissions(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("plan") plan: String,
        @Body groupMissionId: GroupMissionIdRequest
    ): Response<AckResponse>

    @Headers(
        "Authorization: Basic MTc4MTMyOnRuc09ZNGRvUGVPeHhnWg==",
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("commons/service/config")
    suspend fun getUrls(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
    ): Response<UrlResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("users/rewards")
    suspend fun getPrizes(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
    ): Response<PrizesResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("cities/{cityId}")
    suspend fun getCityInfo(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
        @Path("cityId") cityId: Long
    ): Response<CityInfoResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("cities")
    suspend fun getCityList(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
    ): Response<ListCityResponse>

    @Headers(
        "Content-Type: application/json;charset=utf-8",
        "Accept: */*"
    )
    @GET("users/leaderboard")
    suspend fun getBoards(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
    ): Response<BoardsResponse>

    @GET("users/home")
    suspend fun getInfosProfileHome(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
    ): Response<InfosProfileHomeResponse>

    @POST("prize/redeem")
    suspend fun getRedeemPrize(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("plan") plan: String,
        @Header("tier") tier: String,
        @Body requestReddemPrize: RequestReddemPrize
    ): Response<RedeemPrizeResponse>

    @POST("event/register")
    suspend fun registerEvent(
        @Header("serviceId") serviceId: Int = 178132,
        @Header("msisdn") msisdn: Long,
        @Header("lang") lang: String,
        @Header("tier") tier: String,
        @Header("plan") plan: String,
        @Body eventReport: EventReport
    ): Response<AckResponse>

}