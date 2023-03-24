package com.timwe.tti2sdk.data.entity

import com.google.gson.Gson
import com.timwe.tti2sdk.data.model.response.*
import java.io.Serializable

data class Avatar(
    val profileName: String,
    val hair: Options,
    val bottomClothes: Options,
    val topClothes: Options,
    val eyeBrows:  Options,
    val hairColor: Options,
    val skinColor: Options,
    val bottomClothesColor: Options,
    val eyeColor: Options,
    val ridesColor: Options,
    val topClothesColor: Options,
    val gender: Options,
    val shoes: Options,
    val shoesColor: Options,
    val rides: Options,
    val headCustomizations: AvatarCustomizationsResponse,
    val clothesCustomizations: AvatarCustomizationsResponse,
    val shoesCustomizations: AvatarCustomizationsResponse,
    val ridesCustomizations: AvatarCustomizationsResponse
): Serializable{

    fun clone(): Avatar{
        val avatar = Gson().toJson(this, Avatar::class.java)
        return Gson().fromJson(avatar, Avatar::class.java)
    }

}