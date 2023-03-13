package com.timwe.tti2sdk.data.net.repository.local

import com.timwe.tti2sdk.data.net.api.Results
import kotlinx.coroutines.flow.Flow

interface SharedPrefRepository{

    suspend fun getUserId(): String

    suspend fun saveUserId(userId: String)

    suspend fun isUserHasAvatar(userId: String): Boolean

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?

}