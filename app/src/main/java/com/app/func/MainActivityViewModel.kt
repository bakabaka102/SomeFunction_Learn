package com.app.func

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.func.networks.RetrofitObject
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MainActivityViewModel(val app: Application) : AndroidViewModel(app) {

    private val apiService = RetrofitObject.apiService
    private val repository = MainActivityRepository(apiService)
    val note: LiveData<String> get() = repository.note
    val error: LiveData<String> get() = repository.error
    val response: LiveData<ResponseBody> get() = repository.body

//    fun <T> Flow<T>.stateIn(){
//
//    }

    fun getNote() {
        viewModelScope.launch {
            repository.getNote()
        }
    }

    fun downloadNote() {
        viewModelScope.launch {
            repository.downloadNote()
        }
    }

    fun saveFileToDisk(
        context: Context,
        responseBody: ResponseBody,
        fileName: String,
    ) {
        viewModelScope.launch {
            repository.saveFileToDisk(context, responseBody, fileName)
        }
    }
}