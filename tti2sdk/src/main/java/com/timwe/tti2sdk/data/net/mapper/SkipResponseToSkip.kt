package com.timwe.tti2sdk.data.net.mapper

import com.timwe.tti2sdk.data.entity.Skip
import com.timwe.tti2sdk.data.model.response.SkipResponse
import com.timwe.tti2sdk.data.net.data.Mapper

class SkipResponseToSkip: Mapper<SkipResponse, Skip>() {

    override fun transform(item: SkipResponse): Skip {
        return Skip(
            skipped = item.skipped,
            status = item.status
        )
    }

}