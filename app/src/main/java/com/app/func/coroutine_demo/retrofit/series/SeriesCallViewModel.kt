package com.app.func.coroutine_demo.retrofit.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.func.base_content.UiState
import com.app.func.coroutine_demo.data.repository.SeriesRepository
import com.app.func.networks.ApiConstants
import com.app.func.networks.ApiService
import com.app.func.networks.User
import com.app.func.networks.RetrofitObjectGson
import kotlinx.coroutines.launch

class SeriesCallViewModel : ViewModel() {
    private val retrofitService =
        RetrofitObjectGson.getRetrofit(ApiConstants.BASE_URL_USER).create(ApiService::class.java)
    private val repository = SeriesRepository(retrofitService)

    val uiState: LiveData<UiState<List<User>>> get() = repository.uiState

    fun fetchUsers() {
        viewModelScope.launch {
            repository.fetchUsers()
        }
    }

}