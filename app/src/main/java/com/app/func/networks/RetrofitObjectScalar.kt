package com.app.func.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitObjectScalar {

    fun getRetrofit(url: String = ApiConstants.BASE_URL): Retrofit =
        Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    //val apiService: IApiService = getRetrofit.create(IApiService::class.java)
    val apiService: IJsonPlaceHolderService by lazy {
        getRetrofit().create(IJsonPlaceHolderService::class.java)
    }
}

val okHttpClient: OkHttpClient = OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS)
    .writeTimeout(3, TimeUnit.SECONDS).connectTimeout(3, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true).build()