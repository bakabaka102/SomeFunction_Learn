package com.app.func.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObjectScalar {
    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().readTimeout(5000L, TimeUnit.MILLISECONDS)
            .writeTimeout(5, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()


    private val getRetrofit: Retrofit =
        Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    val apiService: IApiService = getRetrofit.create(IApiService::class.java)
}
