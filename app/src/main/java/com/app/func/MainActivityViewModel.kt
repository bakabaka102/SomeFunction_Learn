package com.app.func

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.func.networks.IApiService
import com.app.func.networks.RetrofitObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class MainActivityViewModel(val app: Application) : AndroidViewModel(app) {

    val apiService = RetrofitObject.apiService
    val repository = MainActivityRepository(apiService)
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

class MainActivityRepository(val apiService: IApiService) {

    private val _note = MutableLiveData<String>()
    val note: LiveData<String> get() = _note
    private val _body = MutableLiveData<ResponseBody>()
    val body: LiveData<ResponseBody> get() = _body
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _isLoading = MutableLiveData<String>()
    val isLoading: LiveData<String> get() = _isLoading

    suspend fun getNote() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getNote()
                _note.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
        }
    }

    suspend fun downloadNote() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.downloadNote()
                _body.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
        }
    }

    suspend fun saveFileToDisk(
        context: Context,
        responseBody: ResponseBody,
        fileName: String
    ) {
        withContext(Dispatchers.IO) {
            com.app.func.io.saveFileToDisk(context, responseBody, fileName).also {
                Log.d("fileTag", "File saved to disk ${it?.path}")
            }
        }
    }

}