package com.app.func.networks

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitObject {
    val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().readTimeout(5000L, TimeUnit.MILLISECONDS)
            .writeTimeout(5, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()

    val gson: Gson = GsonBuilder().setLenient().create()

    private val getRetrofit: Retrofit =
        Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            /*.addConverterFactory(MoshiConverterFactory.create(moshi))*/
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            /*.addConverterFactory(GsonConverterFactory.create(gson))*/.build()

    val apiService: IApiService = getRetrofit.create(IApiService::class.java)
}
