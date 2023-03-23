package com.timwe.tti2sdk.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class RequestCreateOrUpdateUser (
    @SerializedName("profile")
    val userAvatarRequest: CreateOrUpdateUserRequest
)

data class CreateOrUpdateUserRequest(

    @SerializedName("HAIR")
    var hair: String,

    @SerializedName("TOP_CLOTHES")
    var topClothes: String,

    @SerializedName("BOTTOM_CLOTHES")
    var bottomClothes: String,

    @SerializedName("SKIN_COLOR")
    var skinColor: String,

    @SerializedName("HAIR_COLOR")
    var hairColor: String,

    @SerializedName("EYEBROWS")
    var eyeBrows: String,

    @SerializedName("BOTTOM_CLOTHES_COLOR")
    var bottomClothesColor: String,

    @SerializedName("EYE_COLOR")
    var eyeColor: String,

    @SerializedName("PROFILE_NAME")
    var profileName: String,

    @SerializedName("TOP_CLOTHES_COLOR")
    var topClothesColor: String,

    @SerializedName("RIDES_COLOR")
    var ridesColor: String,

    @SerializedName("GENDER")
    var gender: String,

    @SerializedName("SHOES")
    var shoes: String,

    @SerializedName("RIDES")
    var rides: String,
){

    fun clone(): CreateOrUpdateUserRequest {
        val createOrUpdateUserRequest = Gson().toJson(this, CreateOrUpdateUserRequest::class.java)
        return Gson().fromJson(createOrUpdateUserRequest, CreateOrUpdateUserRequest::class.java)
    }

}
