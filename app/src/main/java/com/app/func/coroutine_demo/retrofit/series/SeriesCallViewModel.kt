package com.app.func.coroutine_demo.retrofit.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.func.base_content.ResultState
import com.app.func.coroutine_demo.data.repository.SeriesRepository
import com.app.func.networks.ApiConstants
import com.app.func.networks.ApiService
import com.app.func.networks.User
import com.app.func.networks.RetrofitObjectGson
import kotlinx.coroutines.launch

class SeriesCallViewModel : ViewModel() {
    private val apiService =
        RetrofitObjectGson.getRetrofit(ApiConstants.BASE_URL_USER).create(ApiService::class.java)
    private val repository = SeriesRepository(apiService)

    val resultState: LiveData<ResultState<List<User>>> get() = repository.resultState

    fun fetchUsers() {
        viewModelScope.launch {
            repository.fetchUsers()
        }
    }

}