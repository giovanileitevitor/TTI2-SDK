package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource

class SharedPrefUseCaseImpl(
    private val sharedPrefDataSource: SharedPrefDataSource
): SharedPrefUseCase {

    override suspend fun saveCheckupTerms(keyValue: Boolean) {
        sharedPrefDataSource.saveCheckupTerms(termsAccepted = keyValue)
    }

    override suspend fun getCheckupTerms(): Boolean {
        return sharedPrefDataSource.getCheckupTermsStatus()
    }

    override suspend fun getUrls(): UrlAddress {
        return sharedPrefDataSource.getUrls()
    }
}