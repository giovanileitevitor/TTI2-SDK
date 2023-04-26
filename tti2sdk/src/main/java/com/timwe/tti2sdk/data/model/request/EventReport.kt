package com.timwe.tti2sdk.data.model.request

import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue

data class EventReport(
    val eventType: EventType,
    val eventValue: EventValue?,
    val buttonKey: ButtonKey?,
    val utmSource: String,
    val utmMedium: String,
    val utmCampaign: String,
    val utmTerm: String,
    val utmContent: String
)
