package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.repository.local.SharedPrefDataSource
import com.timwe.tti2sdk.data.net.repository.remote.UrlDataSource

class UrlUseCaseImpl(
    private val urlDataSource: UrlDataSource,
    private val sharedPrefDataSource: SharedPrefDataSource
): UrlUseCase {

    override suspend fun getUrls(): Results<UrlAddress> {
        return urlDataSource.getUrls()
    }

    override suspend fun saveUrls(UrlAddress: UrlAddress){
        sharedPrefDataSource.saveUrls(UrlAddress)
    }

}