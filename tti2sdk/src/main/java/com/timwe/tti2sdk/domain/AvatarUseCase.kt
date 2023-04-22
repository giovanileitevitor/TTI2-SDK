package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.ProfileInfo
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.request.RequestReedenMission
import com.timwe.tti2sdk.data.model.response.AckResponse
import com.timwe.tti2sdk.data.net.api.Results

interface AvatarUseCase {

    suspend fun getAvatar(random: Boolean): Results<Avatar>

    suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar>

    suspend fun getFistAccessAvatar(): Boolean

    suspend fun saveFirstAcessavatar(isFistAcsess: Boolean)

    suspend fun saveMissionCompleteAvatar(requestMission: RequestReedenMission): Results<Ack>

    suspend fun getProfileInfos(): Results<ProfileInfo>

}