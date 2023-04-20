package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.entity.Avatar
import com.timwe.tti2sdk.data.entity.UserAndAvatar
import com.timwe.tti2sdk.data.model.request.RequestCreateOrUpdateUser
import com.timwe.tti2sdk.data.model.request.RequestReedenMission
import com.timwe.tti2sdk.data.model.response.AckResponse
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.remote.AvatarDataSource

class AvatarUseCaseImpl(
    private val avatarDataSource: AvatarDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource
) : AvatarUseCase {

    override suspend fun getAvatar(random: Boolean): Results<Avatar> {
        return avatarDataSource.getAvatar(random)
    }

    override suspend fun postCreatOrUpdateUser(userAvatar: RequestCreateOrUpdateUser): Results<UserAndAvatar> {
        return avatarDataSource.postCreatOrUpdateUser(userAvatar)
    }

    override suspend fun getFistAccessAvatar(): Boolean {
        return sharedPrefDataSource.getCheckupTermsStatus()
    }

    override suspend fun saveFirstAcessavatar(isFistAcsess: Boolean) {
        sharedPrefDataSource.saveCheckupTerms(isFistAcsess)
    }

    override suspend fun saveMissionCompleteAvatar(requestMission: RequestReedenMission): Results<Ack> {
        return avatarDataSource.saveMissionCompleteAvatar(requestMission = requestMission)
    }

}