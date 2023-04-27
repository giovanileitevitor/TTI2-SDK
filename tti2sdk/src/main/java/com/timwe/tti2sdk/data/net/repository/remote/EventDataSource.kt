package com.timwe.tti2sdk.data.net.repository.remote

import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.net.api.Results

interface EventDataSource {

    suspend fun registerEvent(eventType: EventType, eventValue: EventValue?): Results<Ack>

    suspend fun registerEventWithKey(eventType: EventType, eventValue: EventValue, buttonKey: ButtonKey): Results<Ack>
}