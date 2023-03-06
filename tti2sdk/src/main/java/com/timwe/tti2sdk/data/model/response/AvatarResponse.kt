package com.timwe.tti2sdk.data.model.response

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
    val userAvatar: UserAvatarResponse,
    val avatarCustomizations: AvatarCustomizationsResponse
)

data class UserAvatarResponse(

    @SerializedName("HAIR")
    val hair: Property,

    @SerializedName("BOTTOM_CLOTHES")
    val bottomClothes: Property,

    @SerializedName("TOP_CLOTHES")
    val topClothes: Property,

    @SerializedName("EYEBROWS")
    val eyeBrows: Property,

    @SerializedName("HAIR_COLOR")
    val hairColor: Property,

    @SerializedName("SKIN_COLOR")
    val skinColor: Property,

    @SerializedName("BOTTOM_CLOTHES_COLOR")
    val bottomClothesColor: Property,

    @SerializedName("EYE_COLOR")
    val eyeColor: Property,

    @SerializedName("PROFILE_NAME")
    val profileName: ProfileName,

    @SerializedName("RIDES_COLOR")
    val ridesColor: Property,

    @SerializedName("TOP_CLOTHES_COLOR")
    val topClothesColor: Property,

    @SerializedName("GENDER")
    val gender: Property,

    @SerializedName("SHOES")
    val shoes: Property,

    @SerializedName("RIDES")
    val rides: Property,

)

data class Property(
    val id:	Int,
    val imageUrl: String,
    val colorCode: String,
    val criteria: String,
    val tags: List<String> = listOf()
)

data class AvatarCustomizationsResponse(

    @SerializedName("HEAD")
    val head: Head,

    @SerializedName("CLOTHES")
    val clothes: Clothes,

    @SerializedName("SHOES")
    val shoes: Shoes,

    @SerializedName("RIDES")
    val rides: Rides

)

data class Head (
    val label: String,
    val customizations: HeadCustomizations
)

data class HeadCustomizations (
    @SerializedName("PROFILE_NAME")
    val profileName: ProfileName,

    @SerializedName("GENDER")
    val gender: BottomClothesColor,

    @SerializedName("SKIN_COLOR")
    val skinColor: ProfileName,

    @SerializedName("HAIR")
    val hair: ProfileName,

    @SerializedName("HAIR_COLOR")
    val hairColor: HairColor,

    @SerializedName("EYE_COLOR")
    val eyeColor: BottomClothesColor,

    @SerializedName("EYEBROWS")
    val eyebrows: BottomClothesColor
)

data class Clothes (
    val label: String,
    val customizations: ClothesCustomizations
)

data class Shoes (
    val label: String,
    val customizations: ShoesCustomizations
)

data class Rides (
    val label: String,
    val customizations: RidesCustomizations
)

data class RidesCustomizations (
    @SerializedName("RIDES")
    val rides: BottomClothesColor,

    @SerializedName("RIDES_COLOR")
    val ridesColor: ProfileName
)

data class ProfileName (
    val label: String,
    val inputType: String,
    val options: List<Option> = listOf(),
    val userOptionIdx: Int,
    val value: String,
)

data class ClothesCustomizations (
    @SerializedName("TOP_CLOTHES")
    val topClothes: ProfileName,

    @SerializedName("TOP_CLOTHES_COLOR")
    val topClothesColor: BottomClothesColor,

    @SerializedName("BOTTOM_CLOTHES")
    val bottomClothes: ProfileName,

    @SerializedName("BOTTOM_CLOTHES_COLOR")
    val bottomClothesColor: BottomClothesColor
)

data class BottomClothesColor (
    val inputType: String,
    val options: List<Option>,
    val userOptionIdx: Int,
    val label: String,
)

data class HairColor (
    val inputType: String,
    val options: List<HairColorElement>,
    val userOptionIdx: Int
)

data class HairColorElement (
    val id: Int,

    @SerializedName("imageUrl")
    val imageURL: String,

    val colorCode: String
)

data class ShoesCustomizations (
    @SerializedName("SHOES")
    val shoes: ProfileName
)

data class Option (
    val id: Int,

    @SerializedName("imageUrl")
    val imageURL: String,

    val tags: List<String>? = listOf(),
    val criteria: String
)




