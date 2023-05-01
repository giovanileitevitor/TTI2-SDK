package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AvatarResponse(
    @SerializedName("userAvatar")
    val userAvatar: UserAvatarResponse,

    @SerializedName("avatarCustomizations")
    val avatarCustomizations: List<AvatarCustomizationsResponse>

): Serializable

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

    @SerializedName("SHOES_COLOR")
    val shoesColor: Options,

    @SerializedName("RIDES")
    val rides: Options,

): Serializable

data class AvatarCustomizationsResponse(
    @SerializedName("key")
    val key: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("customizations")
    var customizations: List<ItemCustomizations>

): Serializable

data class ItemCustomizations (
    @SerializedName("key")
    val key: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("inputType")
    val inputType: String,

    @SerializedName("options")
    var options: List<Options>,

    @SerializedName("riveInputKey")
    val riveInputKey: String,

    @SerializedName("value")
    val nameAvatar: String,

    ): Serializable

data class Options (
    @SerializedName("id")
    val id: Int,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("colorCode")
    val colorCode: String,

    @SerializedName("criteria")
    val criteria: String,

    @SerializedName("value")
    val profileName: String,

    @SerializedName("gender")
    val gender: String, // filtro por esta propriedade

    @SerializedName("riveInputValue")
    val riveInputValue: String,

    @SerializedName("tags")
    val tags: List<String>,

    ): Serializable
