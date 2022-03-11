package com.app.func.coroutine_demo.retrofit.single

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SingleCallNetworkViewModel() : ViewModel() {

    private val users = MutableLiveData<String>()

    init {
        fetchUsers()
    }

    fun getUsers() = users

    private fun fetchUsers() {
        viewModelScope.launch {

        }
    }
}