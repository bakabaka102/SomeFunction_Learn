package hn.single.server.data.network

import hn.single.server.common.Const
import hn.single.server.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(".")
    suspend fun getMoviesData(
        @Query("s") s: String = "army",
        @Query("apikey") apikey: String = Const.API_KEY
    ): ApiResponse
}