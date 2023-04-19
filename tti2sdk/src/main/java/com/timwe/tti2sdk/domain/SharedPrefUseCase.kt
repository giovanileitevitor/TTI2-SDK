package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.UrlAddress

interface SharedPrefUseCase {

    suspend fun saveCheckupTerms(keyValue: Boolean)

    suspend fun getCheckupTerms(): Boolean

    suspend fun getUrls(): UrlAddress

    suspend fun saveDataFromApp(
        msisdn: Long,
        email: String,
        language: String,
        tier: String
    )

    suspend fun setDebugStatus(debugStatus: Boolean)

    suspend fun getMsIsdn(): Long?

    suspend fun getAvatarTier(): String?

}