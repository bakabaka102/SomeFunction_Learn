package com.app.func.networks

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    fun getRetrofit(url: String): Retrofit {
        val gson: Gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(url).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

//    val apiService: QuoteApi = getRetrofit().create(QuoteApi::class.java)
}