package com.timwe.tti2sdk.data.net.data

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuild {

    inline fun <reified T> makeService(baseUrl: String): T {
        return OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build().let {
                retrofitCreate(baseUrl, it)
            }
    }

    inline fun <reified T> makeService(baseUrl: HttpUrl): T {
        return OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build().let {
                retrofitCreate(baseUrl, it)
            }
    }

    inline fun <reified T> retrofitCreate(baseUrl: String, okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return retrofit.create(T::class.java)
    }

    inline fun <reified T> retrofitCreate(baseUrl: HttpUrl, okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return retrofit.create(T::class.java)
    }

}