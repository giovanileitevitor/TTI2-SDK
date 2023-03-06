package com.timwe.tti2sdk.data.entity

import com.timwe.tti2sdk.data.model.response.*

data class Avatar(
    val profileName: ProfileName,
    val hair: Property,
    val bottomClothes: Property,
    val topClothes: Property,
    val eyeBrows:  Property,
    val hairColor: Property,
    val skinColor: Property,
    val bottomClothesColor: Property,
    val eyeColor: Property,
    val ridesColor: Property,
    val topClothesColor: Property,
    val gender: Property,
    val shoes: Property,
    val rides: Property,
    val headCustomizations: Head,
    val clothesCustomizations: Clothes,
    val shoesCustomizations: Shoes,
    val ridesCustomizations: Rides
)