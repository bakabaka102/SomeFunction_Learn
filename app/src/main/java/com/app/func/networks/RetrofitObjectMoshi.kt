package com.app.func.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitObjectMoshi {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun getRetrofit(url: String = ApiConstants.BASE_URL): Retrofit =
        Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    //val apiService: IApiService = getRetrofit.create(IApiService::class.java)
}
