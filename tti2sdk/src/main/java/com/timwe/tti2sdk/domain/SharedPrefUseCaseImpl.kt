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

    override suspend fun saveDataFromApp(msisdn: Long, email: String, language: String, tier: String, plan: String, location: String) {
        sharedPrefDataSource.saveDataFromApp(
            msisdn = msisdn,
            email = email,
            language = language,
            tier = tier,
            plan = plan,
            location = location
        )
    }

    override suspend fun setDebugStatus(debugStatus: Boolean){
        sharedPrefDataSource.setDebugStatus(
            debugStatus = debugStatus
        )
    }

    override suspend fun getMsIsdn(): Long? {
        return sharedPrefDataSource.getMsIsdn()
    }

    override suspend fun getAvatarTier(): String? {
        return sharedPrefDataSource.getAvatarTier()
    }

    override suspend fun getCityId(cityNumber: Long): Long {
        return sharedPrefDataSource.getCityId(cityNumber = cityNumber)
    }

    override suspend fun saveToken(token: String) {
        sharedPrefDataSource.saveToken(token = token)
    }

    override suspend fun getStoredToken(): String {
        return sharedPrefDataSource.getStoredToken()
    }
}