package com.app.func.coroutine_demo.retrofit.base

import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.coroutine_demo.retrofit.aaa.Movie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET(ApiConstants.QUOTES)
    suspend fun getQuotes(): Response<QuoteListResponse>

    @GET(ApiConstants.MOVIE_LIST)
    suspend fun getAllMovies() : Response<List<Movie>>

    @GET(ApiConstants.QUOTES)
    fun getQuoteNormal(): Call<QuoteListResponse>
}