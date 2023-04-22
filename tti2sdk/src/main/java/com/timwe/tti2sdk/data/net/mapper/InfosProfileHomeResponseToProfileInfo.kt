package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.ProfileInfo
import com.timwe.tti2sdk.data.model.response.InfosProfileHomeResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class InfosProfileHomeResponseToProfileInfo: Mapper<InfosProfileHomeResponse, ProfileInfo>() {

    override fun transform(item: InfosProfileHomeResponse): ProfileInfo {
        return ProfileInfo(
            userName = item.userInfo.userProfile.userName,
            tierName = item.userInfo.tier.name,
            currentKms = item.userInfo.userJourneyInfo.currentKms,
            remainingKms = item.userInfo.userJourneyInfo.remainingKms
        )
    }

}