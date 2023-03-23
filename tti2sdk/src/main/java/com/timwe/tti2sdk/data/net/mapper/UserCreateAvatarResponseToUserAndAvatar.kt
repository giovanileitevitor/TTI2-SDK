package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.response.*
import com.timwe.tti2sdk.data.net.data.Mapper

class UserCreateAvatarResponseToUserAndAvatar: Mapper<UserCreateAvatarResponse, UserAndAvatar>()  {

    override fun transform(item: UserCreateAvatarResponse): UserAndAvatar {

        return UserAndAvatar(
            userName = item.profile.username,
            plateNumber = item.profile.plateNumber,
            hair = item.profile.userAvatar.hair,
            topClothes = item.profile.userAvatar.topClothes,
            bottomClothes = item.profile.userAvatar.bottomClothes,
            skinColor = item.profile.userAvatar.skinColor,
            hairColor = item.profile.userAvatar.hairColor,
            eyeBrows = item.profile.userAvatar.eyeBrows,
            bottomClothesColor = item.profile.userAvatar.bottomClothesColor,
            eyeColor = item.profile.userAvatar.eyeColor,
            profileNme = item.profile.userAvatar.profileName,
            topClothesColor = item.profile.userAvatar.topClothesColor,
            ridesColor = item.profile.userAvatar.ridesColor,
            gender = item.profile.userAvatar.gender,
            shoes = item.profile.userAvatar.shoes,
            shoesColor = item.profile.userAvatar.shoesColor,
            rides = item.profile.userAvatar.rides,
        )

    }

}