package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.response.*
import com.timwe.tti2sdk.data.net.data.Mapper

class UserCreateAvatarResponseToUserAndAvatar: Mapper<UserCreateAvatarResponse, UserAndAvatar>()  {

    override fun transform(item: UserCreateAvatarResponse): UserAndAvatar {

        var groupMissionId: Int?
        try {
            groupMissionId = item.listMissionGroups.first().listMissions.first().groupMissionId
        }catch (e: java.lang.Exception){
            groupMissionId = null
        }

        var titleMission: String?
        try {
            titleMission = item.listMissionGroups.first().listMissions.first().additionalProperties.missionTitle
        }catch (e: java.lang.Exception){
            titleMission = ""
        }

        var subtitleMission: String?
        try {
            subtitleMission = item.listMissionGroups.first().listMissions.first().additionalProperties.missionDescription
        }catch (e: java.lang.Exception){
            subtitleMission = ""
        }

        var typeDistance: String?
        try {
            typeDistance = item.listMissionGroups.first().listMissions.first().rewardsMission.first().label
        }catch (e: java.lang.Exception){
            typeDistance = ""
        }

        var distanceMission: String?
        try {
            distanceMission = item.listMissionGroups.first().listMissions.first().rewardsMission.first().prizeValue
        }catch (e: java.lang.Exception){
            distanceMission = ""
        }

        return UserAndAvatar(
            userName = item.profile.username,
            plateNumber = null,
            gender = item.profile.userAvatar.gender,
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
            shoes = item.profile.userAvatar.shoes,
            shoesColor = item.profile.userAvatar.shoesColor,
            rides = item.profile.userAvatar.rides,
            ridesColor = item.profile.userAvatar.ridesColor,
            groupMissionId = groupMissionId!!,
            titleMission = titleMission!!,
            subtitleMission = subtitleMission!!,
            typeDistance = typeDistance!!,
            distanceMission = distanceMission!!,
        )

    }

}