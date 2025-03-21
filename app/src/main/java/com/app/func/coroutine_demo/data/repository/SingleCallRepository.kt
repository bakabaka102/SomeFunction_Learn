package com.app.func.coroutine_demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.func.base_content.UiState
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.networks.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SingleCallRepository(private val retrofitService: RetrofitService) : ISingleCallRepository {

    private val _uiState = MutableLiveData<UiState<Response<QuoteListResponse>>>()
    val uiState : LiveData<UiState<Response<QuoteListResponse>>> get() = _uiState

    override suspend fun getAllQuotes() {
        withContext(Dispatchers.IO) {
            _uiState.postValue(UiState.Loading(true))
            kotlin.runCatching {
                val response = retrofitService.getQuotes()
                response?.let {
                    _uiState.postValue(UiState.Success(it))
                }
            }.onFailure {
               _uiState.postValue(UiState.Error(it.message.toString()))
            }
        }
    }
}