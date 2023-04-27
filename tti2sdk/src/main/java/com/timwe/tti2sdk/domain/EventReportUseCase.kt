package com.timwe.tti2sdk.domain

import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue

interface EventReportUseCase {

    suspend fun reportEvent(eventType: EventType, eventValue: EventValue?)

    suspend fun reportEventWithKey(eventType: EventType, eventValue: EventValue, buttonKey: ButtonKey)

}