package com.timwe.tti2sdk.data.net.data

import com.timwe.utils.WifiService
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor: Interceptor {

    companion object {
        const val ERROR_NO_INTERNET_CONNECTION = 512.toString()
        const val ERROR_OTHERS = 500.toString()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WifiService.instance.isOnline()) {
            throw IOException("No internet connection")
        } else {
            return chain.proceed(chain.request())
        }
    }

}

class OAuthInterceptorMy(
    private val tokenType: String,
    private val accessToken: String
) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        if (!WifiService.instance.isOnline()) {
            throw IOException("No internet connection")
        } else {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", "$tokenType $accessToken").build()
            return chain.proceed(request)
        }
    }
}