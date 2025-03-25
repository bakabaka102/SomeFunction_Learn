package com.app.func.coroutine_demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.func.base_content.ResultState
import com.app.func.networks.ApiHelperImpl
import com.app.func.networks.ApiService
import com.app.func.networks.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeriesRepository(apiService: ApiService) {
    private val apiHelper = ApiHelperImpl(apiService)
    private val _resultState = MutableLiveData<ResultState<List<User>>>()
    val resultState: LiveData<ResultState<List<User>>> get() = _resultState

    suspend fun fetchUsers() {
        withContext(Dispatchers.IO) {
            _resultState.postValue(ResultState.Loading(true))
            try {
                val usersFromApi = apiHelper.getUsers()
                val moreUsersFromApi = apiHelper.getMoreUsers()
                val allUsersFromApi = mutableListOf<User>()
                allUsersFromApi.addAll(usersFromApi)
                allUsersFromApi.addAll(moreUsersFromApi)
                _resultState.postValue(ResultState.Success(allUsersFromApi))
            } catch (e: Exception) {
                _resultState.postValue(ResultState.Error(e.message.toString()))
            }
        }
    }
}