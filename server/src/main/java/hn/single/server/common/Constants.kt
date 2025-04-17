package hn.single.server.common

import hn.single.server.BuildConfig

object Constants {
    const val BASE_URL = "https://www.omdbapi.com/"
    const val API_KEY = BuildConfig.API_KEY //"your api key"
    //BuildConfig.API_KEY

    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=86b39afce9f54f57befb44ea9ecfc357
    const val BASE_URL_URL = "https://newsapi.org/"
    const val QUERY_EVERYTHING_URL = "v2/everything"
    const val QUERY_TOP_HEAD_LINES = "v2/top-headlines"

}