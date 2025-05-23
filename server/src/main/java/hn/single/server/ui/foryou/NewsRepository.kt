package hn.single.server.ui.foryou

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hn.single.server.BuildConfig
import hn.single.server.common.UIState
import hn.single.server.data.network.NewsApiService
import hn.single.server.ui.search.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsApi: NewsApiService) {

    private val _response = MutableLiveData<UIState<NewsResponse>>(UIState.Loading)
    val response: LiveData<UIState<NewsResponse>> get() = _response

    suspend fun getTopHeadlines(
        country: String = "vi",
        apiKey: String = BuildConfig.API_KEY,
    ) {
        withContext(Dispatchers.IO) {
            _response.postValue(UIState.Loading)
            runCatching {
                _response.postValue(
                    UIState.Success(
                        newsApi.getTopHeadlines(country = country, apiKey = apiKey)
                    )
                )
            }.onFailure {
                _response.postValue(UIState.Failure(throwable = it))
            }
        }
    }
}