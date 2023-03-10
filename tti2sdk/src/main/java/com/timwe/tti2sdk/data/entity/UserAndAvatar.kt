package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.data.model.response.Options

data class UserAndAvatar(
    val userName: String,
    val plateNumber: String,
    val hair: Options,
    val topClothes: Options,
    val bottomClothes: Options,
    val skinColor: Options,
    val hairColor: Options,
    val eyeBrows: Options,
    val bottomClothesColor: Options,
    val eyeColor: Options,
    val profileNme: Options,
    val topClothesColor: Options,
    val ridesColor: Options,
    val gender: Options,
    val shoes: Options,
    val rides: Options,
)