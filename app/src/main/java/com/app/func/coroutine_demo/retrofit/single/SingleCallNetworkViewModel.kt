package com.app.func.coroutine_demo.retrofit.single

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.coroutine_demo.retrofit.base.ApiConstants
import com.app.func.coroutine_demo.retrofit.base.RetrofitObject
import com.app.func.coroutine_demo.retrofit.base.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SingleCallNetworkViewModel : ViewModel() {

    private val api =
        RetrofitObject.getRetrofit(ApiConstants.BASE_URL_QUOTE).create(RetrofitService::class.java)
    private val quotes = MutableLiveData<Response<QuoteListResponse>>()
    val errorMessage = MutableLiveData<String>()
    private var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    val quoteList: MutableLiveData<Response<QuoteListResponse>> = quotes

    fun getQuotes() {
        job = CoroutineScope(Dispatchers.IO).launch {
            loading.postValue(true)
            val response: Response<QuoteListResponse>? = api.getQuotes()
            withContext(Dispatchers.Main) {
                if (response?.isSuccessful == true) {
//                    movieList.postValue(response.body())
//                    quotes.postValue(response)
                    quotes.value = api.getQuotes()
                    loading.value = false
                } else {
                    errorMessage.postValue(response?.message())
                    loading.postValue(false)
                }
            }
        }
//        viewModelScope.launch {
//            quotes.value = api.getQuotes()
//        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}