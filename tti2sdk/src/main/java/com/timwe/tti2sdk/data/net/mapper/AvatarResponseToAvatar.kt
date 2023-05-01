package com.timwe.tti2sdk.data.net.mapper

import com.google.gson.Gson
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.Mission
import com.timwe.tti2sdk.data.model.response.*
import com.timwe.tti2sdk.data.net.data.Mapper

class AvatarResponseToAvatar: Mapper<AvatarResponse, Avatar>()  {

    override fun transform(item: AvatarResponse): Avatar {

        val gson = Gson()

        val mMineUserEntity1 = gson.toJson(item, AvatarResponse::class.java)
        println(mMineUserEntity1)

        val aux =  Avatar(
            profileName = getNameAvatar(item.avatarCustomizations),
            hair = item.userAvatar.hair,
            bottomClothes = item.userAvatar.bottomClothes,
            topClothes = item.userAvatar.topClothes,
            eyeBrows =  item.userAvatar.eyeBrows,
            hairColor = item.userAvatar.hairColor,
            skinColor = item.userAvatar.skinColor,
            bottomClothesColor = item.userAvatar.bottomClothesColor,
            eyeColor = item.userAvatar.eyeColor,
            ridesColor = item.userAvatar.ridesColor,
            topClothesColor = item.userAvatar.topClothesColor,
            gender = item.userAvatar.gender,
            shoes = item.userAvatar.shoes,
            shoesColor = item.userAvatar.shoesColor,
            rides = item.userAvatar.rides,
            headCustomizations = item.avatarCustomizations.filter{ it.key == "HEAD" }.first(),
            clothesCustomizations = item.avatarCustomizations.filter{ it.key == "CLOTHES" }.first(),
            shoesCustomizations = item.avatarCustomizations.filter{ it.key == "SHOES" }.first(),
            ridesCustomizations = item.avatarCustomizations.filter{ it.key == "RIDES" }.first(),
        )

        val mMineUserEntity2 = gson.toJson(aux, Avatar::class.java)
        println(mMineUserEntity2)

        return aux

    }

    fun getNameAvatar(avatarCustomizations: List<AvatarCustomizationsResponse>): String{
        try {
            val listAux = avatarCustomizations.filter{
                it.key == "HEAD"
            }
            val listaux2 = listAux.first().customizations.filter {
                it.key == "PROFILE_NAME"
            }
            return listaux2.first().nameAvatar
        }catch (e: java.lang.Exception){
            return ""
        }
    }

}