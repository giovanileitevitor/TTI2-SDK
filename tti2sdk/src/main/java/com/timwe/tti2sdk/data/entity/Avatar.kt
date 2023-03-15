package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.data.model.response.*
import java.io.Serializable

data class Avatar(
    val profileName: Options,
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
    val rides: Options,
    val headCustomizations: AvatarCustomizationsResponse,
    val clothesCustomizations: AvatarCustomizationsResponse,
    val shoesCustomizations: AvatarCustomizationsResponse,
    val ridesCustomizations: AvatarCustomizationsResponse
): Serializable