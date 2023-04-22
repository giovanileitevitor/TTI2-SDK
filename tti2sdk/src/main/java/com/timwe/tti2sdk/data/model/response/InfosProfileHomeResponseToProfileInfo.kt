package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class InfosProfileHomeResponse(
    val userInfo: UserInfo
)

data class UserInfo(
    val tier: Tier,
    val userProfile: UserProfile,
    val userJourneyInfo: UserJourneyInfo,
)

data class Tier(
    val name: String,
)

data class UserProfile(
    @SerializedName("username")
    val userName: String,
)

data class UserJourneyInfo(
    val currentKms: Int,
    val remainingKms: Int,
)