package com.timwe.tti2sdk.domain

interface SharedPrefUseCase {

    suspend fun saveCheckupTerms(keyValue: Boolean)

    suspend fun getCheckupTerms(): Boolean

}