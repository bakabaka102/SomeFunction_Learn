package hn.single.server.ui.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hn.single.server.BuildConfig
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceNewsViewModel @Inject constructor(
    private val repository: FinanceNewsRepository
) : ViewModel() {

    val response get() = repository.response

    fun getFinanceArticles(
        query: String = "finance",
        apiKey: String = BuildConfig.API_KEY,
    ) {
        viewModelScope.launch {
            repository.getFinanceArticles(query, apiKey)
        }
    }
}