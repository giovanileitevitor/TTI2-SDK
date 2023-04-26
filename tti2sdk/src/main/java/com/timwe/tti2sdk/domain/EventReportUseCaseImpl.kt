package com.timwe.tti2sdk.domain

import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.data.net.repository.remote.EventDataSource

class EventReportUseCaseImpl(
    private val eventDataSource: EventDataSource
): EventReportUseCase {

    override suspend fun reportEvent(eventType: EventType, eventValue: EventValue?) {
        eventDataSource.registerEvent(eventType = eventType, eventValue = eventValue)
    }

    override suspend fun reportEventWithKey(eventType: EventType, eventValue: EventValue, buttonKey: ButtonKey) {
        eventDataSource.registerEventWithKey(eventType = eventType, eventValue = eventValue, buttonKey = buttonKey)
    }

}