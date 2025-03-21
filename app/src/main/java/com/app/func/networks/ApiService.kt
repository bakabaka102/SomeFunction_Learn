package com.app.func.networks

import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("more-users")
    suspend fun getMoreUsers(): List<User>

    @GET("error")
    suspend fun getUsersWithError(): List<User>

}