package com.app.func.networks

import com.google.gson.annotations.SerializedName

interface ApiHelper {

    suspend fun getUsers(): List<User>

    suspend fun getMoreUsers(): List<User>

    suspend fun getUsersWithError(): List<User>

}

data class User(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
)