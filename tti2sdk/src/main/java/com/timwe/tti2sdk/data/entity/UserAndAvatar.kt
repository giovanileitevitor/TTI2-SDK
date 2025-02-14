package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.data.model.response.Options

data class UserAndAvatar(
    val userName: String,
    val plateNumber: String?,
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
    val gender: Options,
    val shoes: Options,
    val shoesColor: Options,
    val rides: Options,
    val ridesColor: Options,

    //itens case exists mission
    val groupMissionId: Int?,
    val titleMission: String,
    val subtitleMission: String,
    val typeDistance: String,
    val distanceMission: String,

)