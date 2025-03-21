package com.app.func.coroutine_demo.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.func.base_content.UiState
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.coroutine_demo.data.repository.SingleCallRepository
import com.app.func.networks.ApiConstants
import com.app.func.networks.RetrofitService
import com.app.func.networks.RetrofitObjectGson
import kotlinx.coroutines.launch
import retrofit2.Response

class SingleCallNetworkViewModel : ViewModel() {

    private val retrofitService =
        RetrofitObjectGson.getRetrofit(ApiConstants.BASE_URL_QUOTE).create(RetrofitService::class.java)
    private val repository = SingleCallRepository(retrofitService)
    val uiState : LiveData<UiState<Response<QuoteListResponse>>> get() = repository.uiState

    fun getQuotes() {
        viewModelScope.launch {
            repository.getAllQuotes()
        }
    }
}