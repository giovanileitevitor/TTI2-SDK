package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.net.api.Results

interface PrizeDataSource {

    suspend fun getPrize(): Results<PrizeFlow>

}