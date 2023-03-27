package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.model.response.AckResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class AckResponseToAck : Mapper<AckResponse, Ack>(){

    override fun transform(item: AckResponse): Ack {
        return Ack(
            statusCode = item.statusCode ?: 0
        )
    }
}