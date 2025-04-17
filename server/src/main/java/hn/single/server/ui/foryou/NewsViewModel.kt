package hn.single.server.ui.foryou

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hn.single.server.common.UIState
import hn.single.server.ui.search.model.TopHeadlinesResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val topHeadlines: LiveData<UIState<TopHeadlinesResponse>> get() = repository.response

    /*init {
        loadTopHeadLines()
    }*/

    fun loadTopHeadLines(
        country: String = "vi",
        apiKey: String = "86b39afce9f54f57befb44ea9ecfc357",
    ) {
        viewModelScope.launch {
            repository.getTopHeadlines(country, apiKey)
        }
    }
}
