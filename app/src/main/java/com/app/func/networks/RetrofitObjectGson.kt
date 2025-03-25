package com.app.func.networks

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObjectGson {

    private val gson: Gson = GsonBuilder().setLenient().create()

    fun getRetrofit(url: String = ApiConstants.BASE_URL): Retrofit =
        Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    //val apiService: IApiService = getRetrofit.create(IApiService::class.java)
}
