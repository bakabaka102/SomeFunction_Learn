package com.app.func.networks

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObject {

    fun getRetrofit(url: String): Retrofit {
        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build()

        val gson: Gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

//    val apiService: QuoteApi = getRetrofit().create(QuoteApi::class.java)
}