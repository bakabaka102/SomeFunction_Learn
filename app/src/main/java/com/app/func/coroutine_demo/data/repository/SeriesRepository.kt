package com.app.func.coroutine_demo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.func.base_content.UiState
import com.app.func.networks.ApiService
import com.app.func.networks.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeriesRepository(private val apiHelper: ApiService) {

    private val _uiState = MutableLiveData<UiState<List<User>>>()
    val uiState: LiveData<UiState<List<User>>> get() = _uiState

    suspend fun fetchUsers() {
        withContext(Dispatchers.IO) {
            _uiState.postValue(UiState.Loading(true))
            try {
                val usersFromApi = apiHelper.getUsers()
                val moreUsersFromApi = apiHelper.getMoreUsers()
                val allUsersFromApi = mutableListOf<User>()
                allUsersFromApi.addAll(usersFromApi)
                allUsersFromApi.addAll(moreUsersFromApi)
                _uiState.postValue(UiState.Success(allUsersFromApi))
            } catch (e: Exception) {
                _uiState.postValue(UiState.Error(e.message.toString()))
            }
        }
    }
}