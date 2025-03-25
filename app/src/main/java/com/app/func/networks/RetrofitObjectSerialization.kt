package com.app.func.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private val json = Json { ignoreUnknownKeys = true }

class RetrofitObjectSerialization {

    private val contentType = "application/json; charset=UTF8".toMediaType()
    private val factory = json.asConverterFactory(contentType)

    fun getRetrofit(url: String = ApiConstants.BASE_URL): Retrofit =
        Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(factory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    //val apiService: IApiService = getRetrofit.create(IApiService::class.java)
}