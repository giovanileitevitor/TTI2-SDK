package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
    val userAvatar: UserAvatarResponse,
    val avatarCustomizations: List<AvatarCustomizationsResponse>
)

data class UserAvatarResponse(
    @SerializedName("HAIR")
    val hair: Options,

    @SerializedName("BOTTOM_CLOTHES")
    val bottomClothes: Options,

    @SerializedName("TOP_CLOTHES")
    val topClothes: Options,

    @SerializedName("EYEBROWS")
    val eyeBrows: Options,

    @SerializedName("HAIR_COLOR")
    val hairColor: Options,

    @SerializedName("SKIN_COLOR")
    val skinColor: Options,

    @SerializedName("BOTTOM_CLOTHES_COLOR")
    val bottomClothesColor: Options,

    @SerializedName("EYE_COLOR")
    val eyeColor: Options,

    @SerializedName("PROFILE_NAME")
    val profileName: Options,

    @SerializedName("RIDES_COLOR")
    val ridesColor: Options,

    @SerializedName("TOP_CLOTHES_COLOR")
    val topClothesColor: Options,

    @SerializedName("GENDER")
    val gender: Options,

    @SerializedName("SHOES")
    val shoes: Options,

    @SerializedName("RIDES")
    val rides: Options,
)

data class AvatarCustomizationsResponse(
    @SerializedName("key")
    val key: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("customizations")
    val customizations: List<ItemCustomizations>
)

data class ItemCustomizations (
    @SerializedName("key")
    val key: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("inputType")
    val inputType: String,

    @SerializedName("value")
    val avatarName: String,

    @SerializedName("userOptionIdx")
    val userOptionIdx: Int,

    @SerializedName("options")
    val options: List<Options>,
)

data class Options (
    @SerializedName("id")
    val id: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("colorCode")
    val colorCode: String,

    @SerializedName("criteria")
    val criteria: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("tags")
    val tags: List<String>,
)



