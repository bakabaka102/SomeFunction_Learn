package com.app.func.coroutine_demo.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.func.base_content.ResultState
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.coroutine_demo.data.repository.SingleCallRepository
import com.app.func.networks.ApiConstants
import com.app.func.networks.IQuotableService
import com.app.func.networks.RetrofitObjectGson
import kotlinx.coroutines.launch
import retrofit2.Response

class SingleCallNetworkViewModel : ViewModel() {

    private val service =
        RetrofitObjectGson.getRetrofit(ApiConstants.BASE_URL_QUOTE).create(IQuotableService::class.java)
    private val repository = SingleCallRepository(service)
    val resultState : LiveData<ResultState<Response<QuoteListResponse>>> get() = repository.resultState

    fun getQuotes() {
        viewModelScope.launch {
            repository.getAllQuotes()
        }
    }
}