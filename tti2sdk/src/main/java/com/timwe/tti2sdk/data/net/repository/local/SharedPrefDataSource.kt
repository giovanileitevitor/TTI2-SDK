package com.timwe.tti2sdk.data.net.repository.local

interface SharedPrefDataSource{

    suspend fun saveCheckupTerms(termsAccepted : Boolean)

    suspend fun getCheckupTermsStatus(): Boolean

    suspend fun getUserId(): String

    suspend fun saveUserId(userId: String)

    suspend fun isUserHasAvatar(userId: String): Boolean

    suspend fun putString(key: String, value: String)

    suspend fun getString(key: String): String?

    suspend fun saveUrls(urls: List<String>)

}