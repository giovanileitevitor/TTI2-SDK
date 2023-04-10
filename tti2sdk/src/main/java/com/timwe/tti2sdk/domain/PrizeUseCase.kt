package com.timwe.tti2sdk.domain

import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.net.api.Results

interface PrizeUseCase {

    suspend fun getPrizes(): Results<PrizeFlow>

}