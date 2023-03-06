package com.timwe.tti2sdk.net.mapper

import com.timwe.tti2sdk.entity.Example
import com.timwe.tti2sdk.model.response.ExampleResponse
import com.timwe.tti2sdk.net.data.Mapper

class ExampleResponseToExample: Mapper<ExampleResponse, Example>() {

    override fun transform(exampleResponse: ExampleResponse): Example {
        return Example(name = exampleResponse.name)
    }

}