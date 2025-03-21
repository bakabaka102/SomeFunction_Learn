package com.app.func.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private val json = Json { ignoreUnknownKeys = true }

class RetrofitObjectSerialization {

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().readTimeout(5000L, TimeUnit.MILLISECONDS)
            .writeTimeout(5, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()

    private val contentType = "application/json; charset=UTF8".toMediaType()
    private val factory = json.asConverterFactory(contentType)

    private val getRetrofit: Retrofit =
        Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(okHttpClient)
            .addConverterFactory(factory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    val apiService: IApiService = getRetrofit.create(IApiService::class.java)
}