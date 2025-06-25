package hn.single.server.ui.foryou

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hn.single.server.BuildConfig
import hn.single.server.common.UIState
import hn.single.server.ui.search.model.NewsResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val topHeadlines: LiveData<UIState<NewsResponse>> get() = repository.response

    /*init {
        loadTopHeadLines()
    }*/

    fun loadTopHeadLines(
        country: String = "vi",
        apiKey: String = BuildConfig.API_KEY,
    ) {
        viewModelScope.launch {
            //repository.getTopHeadlines(country, apiKey)
            repository.getTopHeadlinesGeneric(country, apiKey)
        }
    }
}
