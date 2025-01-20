package com.app.func.networks

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface IApiService {

    @GET(ApiConstants.NOTE)
    suspend fun getNote(): String

    @GET(ApiConstants.NOTE)
    @Streaming
    suspend fun downloadNote(): ResponseBody
}

data class Note(

    @SerializedName("userId") var userId: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("completed") var completed: Boolean? = null

)