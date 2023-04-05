package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.net.repository.local.SharedPrefRepository

class SharedPrefUseCaseImpl(
    private val sharedPrefRepository: SharedPrefRepository
): SharedPrefUseCase {

    override suspend fun saveCheckupTerms(keyValue: Boolean) {
        sharedPrefRepository.saveCheckupTerms(termsAccepted = keyValue)
    }

    override suspend fun getCheckupTerms(): Boolean {
        return sharedPrefRepository.getCheckupTermsStatus()
    }
}