package com.timwe.tti2sdk.data.net.data

import com.timwe.tti2sdk.data.net.api.ApiError
import com.timwe.tti2sdk.data.net.api.ErrorResults
import com.timwe.tti2sdk.data.net.api.Results
import com.timwe.tti2sdk.data.net.api.SuccessResults
import com.timwe.utils.Utils
import retrofit2.Response

fun <T, O> Response<T>.create(mapper: Mapper<T, O>): Results<O> {
    return transformResponse(this).converter(mapper)
}

fun <T> transformResponse(response: Response<T>): ApiResponse<T> {
    val TAG = "SDK"
    if (response.isSuccessful) {
        val body = response.body()
        val code = response.code()
        if (body == null || response.code() == 204 || (body is List<*> && body.size == 0)){
            Utils.showLog(TAG, "Response with Warnings \n")
            Utils.showLog(TAG, "Response Code: $code \n")
            Utils.showLog(TAG, "Response Body: $body \n")
            Utils.showLog(TAG, "Response Headers: ${response.headers()}\n")
            return ApiErrorResponse(
                ApiError(response.code().toString(), response.message())
            )
        }else {
            Utils.showLog(TAG, "Response Successfull \n")
            Utils.showLog(TAG, "Response Code: $code \n")
            Utils.showLog(TAG, "Response Body: $body \n --> endbody")
            Utils.showLog(TAG, "Response Headers: ${response.headers()}\n")
            return ApiSuccessResponse(body = body)
        }
    } else {
        Utils.showLog(TAG, "Response with Errors\n")
        Utils.showLog(TAG, "Response Code: ${response.code()}\n")
        Utils.showLog(TAG, "Response Message: ${response.message()}\n")
        Utils.showLog(TAG, "Response Headers: ${response.headers()}\n")
        if(response.body() != null){
            Utils.showLog(TAG, "Response Body: ${response.body()} \n --> endbody")
        }
        return ApiErrorResponse(
            ApiError(
                response.code().toString(),
                response.message()
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