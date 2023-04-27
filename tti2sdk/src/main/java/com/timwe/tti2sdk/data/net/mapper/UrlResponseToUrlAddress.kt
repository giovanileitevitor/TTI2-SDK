package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.UrlAddress
import com.timwe.tti2sdk.data.model.response.UrlResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class UrlResponseToUrlAddress: Mapper<UrlResponse, UrlAddress>() {

    override fun transform(item: UrlResponse): UrlAddress {
        return UrlAddress(
            moveNextStage = item.additionalInfo.moveNextStage,
            howToWin = item.additionalInfo.howToWin,
            learnMore = item.additionalInfo.learnMore,
            finalPrize = item.additionalInfo.finalPrize,
            rulesPrizes = item.additionalInfo.rulesPrizes,
            termsAndConditions = item.additionalInfo.termsAndConditions,
            userRegistered = item.userRegistered ?: false,
            token = item.token
        )
    }

}