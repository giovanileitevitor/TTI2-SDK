package com.timwe.tti2sdk.data.net.repository.local

import com.timwe.tti2sdk.data.entity.UrlAddress

interface SharedPrefDataSource{

    suspend fun saveCheckupTerms(termsAccepted : Boolean)

    suspend fun getCheckupTermsStatus(): Boolean

    suspend fun getUserId(): String

    suspend fun saveUserId(userId: String)

    suspend fun isUserHasAvatar(userId: String): Boolean

    suspend fun isFistAccessAvatar(): Boolean

    suspend fun saveFistAccessAvatar(isFistAcsess : Boolean)

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?

    suspend fun saveUrls(urlAddress: UrlAddress)

    suspend fun getUrls(): UrlAddress

}