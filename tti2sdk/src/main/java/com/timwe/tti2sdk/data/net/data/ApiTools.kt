package com.timwe.tti2sdk.data.net.data

import android.util.Log
import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.api.SuccessResults
import retrofit2.Response

fun <T, O> Response<T>.create(mapper: Mapper<T, O>): Results<O> {
    return transformResponse(this).converter(mapper)
}

fun <T> transformResponse(response: Response<T>): ApiResponse<T> {
    if (response.isSuccessful) {
        val body = response.body()
        if (body == null || response.code() == 204 || (body is List<*> && body.size == 0)){
            return ApiErrorResponse(
                ApiError(response.code().toString(), response.message())
            )
        }else {
            return ApiSuccessResponse(body = body)
        }
    } else {
        return ApiErrorResponse(
            ApiError(response.code().toString(), response.message()
            )
        )
    }
}

sealed class ApiResponse<T>
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
data class ApiErrorResponse<T>(val error: ApiError) : ApiResponse<T>()

private fun <T, O> ApiResponse<T>.converter(mapper: Mapper<T, O>): Results<O> {
    return when (this) {
        is ApiSuccessResponse -> {
            val itemConverted = mapper.transform(body)
            SuccessResults(itemConverted)
        }
        is ApiErrorResponse -> ErrorResults(
            error
        )
    }
}