package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.net.api.Results

interface EventDataSource {

    suspend fun registerEvent( ): Results<Ack>

}