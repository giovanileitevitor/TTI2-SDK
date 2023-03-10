package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class UserCreateAvatarResponse(
    @SerializedName("errorTO")
    val erro: ErrorTo,

    @SerializedName("profile")
    val profile: ProfileUser,

    @SerializedName("newMessages")
    val newMessages: Boolean,

    @SerializedName("userChangedCity")
    val userChangedCity: Boolean,
)

data class ErrorTo(
    @SerializedName("code")
    val code: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("error")
    val error: Boolean,
)

data class ProfileUser(
    @SerializedName("username")
    val username: String,

    @SerializedName("plateNumber")
    val plateNumber: String,

    @SerializedName("userAvatar")
    val userAvatar: UserAvatarResponse,
)