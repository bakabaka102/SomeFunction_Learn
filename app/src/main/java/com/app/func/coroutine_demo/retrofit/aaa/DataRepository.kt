package com.app.func.coroutine_demo.retrofit.aaa

import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.coroutine_demo.retrofit.base.RetrofitService
import retrofit2.Response

class DataRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies(): Response<List<Movie>> = retrofitService.getAllMovies()

    suspend fun getAllQuotes(): Response<QuoteListResponse> = retrofitService.getQuotes()

}