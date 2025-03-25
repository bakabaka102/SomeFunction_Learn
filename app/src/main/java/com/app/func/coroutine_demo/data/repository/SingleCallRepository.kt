package com.app.func.coroutine_demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.func.base_content.ResultState
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.networks.IQuotableService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SingleCallRepository(private val service: IQuotableService) : ISingleCallRepository {

    private val _resultState = MutableLiveData<ResultState<Response<QuoteListResponse>>>()
    val resultState: LiveData<ResultState<Response<QuoteListResponse>>> get() = _resultState

    override suspend fun getAllQuotes() {
        withContext(Dispatchers.IO) {
            _resultState.postValue(ResultState.Loading(true))
            runCatching {
                val response = service.getQuotes()
                response?.let {
                    _resultState.postValue(ResultState.Success(it))
                }
            }.onFailure {
                _resultState.postValue(ResultState.Error(it.message.toString()))
            }
        }
    }
}