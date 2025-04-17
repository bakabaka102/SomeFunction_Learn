package hn.single.server.data.network

import hn.single.server.common.Constants
import hn.single.server.data.model.ApiResponse
import hn.single.server.ui.search.model.NewsResponse
import hn.single.server.ui.search.model.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET(".")
    suspend fun getMoviesData(
        @Query("s") s: String = "army",
        @Query("apikey") apikey: String = Constants.API_KEY
    ): ApiResponse

    @GET(Constants.QUERY_EVERYTHING_URL)
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
    ): NewsResponse

    @GET(Constants.QUERY_TOP_HEAD_LINES)
    suspend fun getTopHeadlines(
        @Query("country") country: String = "vi",
        @Query("apiKey") apiKey: String,
    ): TopHeadlinesResponse
}