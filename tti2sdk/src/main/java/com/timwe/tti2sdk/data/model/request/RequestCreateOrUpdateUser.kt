package com.timwe.tti2sdk.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestCreateOrUpdateUser (
    @SerializedName("profile")
    val userAvatarRequest: UserAvatarRequest
)

data class CreateOrUpdateUserRequest(
    @SerializedName("HAIR")
    val hair: String,

    @SerializedName("TOP_CLOTHES")
    val topClothes: String,

    @SerializedName("BOTTOM_CLOTHES")
    val bottomClothes: String,

    @SerializedName("SKIN_COLOR")
    val skinClor: String,

    @SerializedName("HAIR_COLOR")
    val hairColor: String,

    @SerializedName("EYEBROWS")
    val eyeBrows: String,

    @SerializedName("BOTTOM_CLOTHES_COLOR")
    val bottomClothesColor: String,

    @SerializedName("EYE_COLOR")
    val eyeColor: String,

    @SerializedName("PROFILE_NAME")
    val profileName: String,

    @SerializedName("_id")
    val topClothesColor: String,

    @SerializedName("TOP_CLOTHES_COLOR")
    val ridesColor: String,

    @SerializedName("GENDER")
    val gender: String,

    @SerializedName("SHOES")
    val shoes: String,

    @SerializedName("RIDES")
    val rides: String,
)
