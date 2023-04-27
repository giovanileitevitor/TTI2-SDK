package com.timwe.tti2sdk.data.net.repository.remote

import android.content.Context
import com.timwe.init.ButtonKey
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.BuildConfig
import com.timwe.tti2sdk.data.entity.Ack
import com.timwe.tti2sdk.data.model.request.EventReport
import com.timwe.tti2sdk.data.model.response.AckResponse
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.data.create
import com.timwe.tti2sdk.data.net.mapper.AckResponseToAck
import com.timwe.tti2sdk.data.net.services.API
import com.timwe.tti2sdk.di.Application
import com.timwe.utils.Utils

class EventDataSourceImpl(
    private val api: API,
    private val mapperAckResponseToAck: AckResponseToAck,
    private val context: Context
) : EventDataSource{

    override suspend fun registerEvent(eventType: EventType, eventValue: EventValue?): Results<Ack> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}event/register")
        return api.registerEvent(
            serviceId = 178132,
            msisdn = (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile().lang!!,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile().tier!!,
            eventReport = EventReport(
                eventType = eventType,
                eventValue = eventValue ?: null,
                buttonKey = null,
                utmSource = "",
                utmMedium = "",
                utmCampaign = "",
                utmTerm = "",
                utmContent = ""
            )
        ).create(mapperAckResponseToAck)

    }

    override suspend fun registerEventWithKey(eventType: EventType, eventValue: EventValue, buttonKey: ButtonKey): Results<Ack> {
        Utils.showLog("SDK", "Request: ${BuildConfig.BASE_URL}event/register")
        return api.registerEvent(
            serviceId = 178132,
            msisdn = (context as Application).getUserProfile().userMsisdn!!.toLong(),
            lang =  (context as Application).getUserProfile().lang!!,
            plan = (context as Application).getUserProfile().plan,
            tier =  (context as Application).getUserProfile().tier!!,
            eventReport = EventReport(
                eventType = eventType,
                eventValue = eventValue ?: null,
                buttonKey = buttonKey,
                utmSource = "",
                utmMedium = "",
                utmCampaign = "",
                utmTerm = "",
                utmContent = ""
            )
        ).create(mapperAckResponseToAck)
    }
}