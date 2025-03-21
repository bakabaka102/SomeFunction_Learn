package com.app.func

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.func.networks.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class MainActivityRepository(private val apiService: IApiService) {

    private val _note = MutableLiveData<String>()
    val note: LiveData<String> get() = _note
    private val _body = MutableLiveData<ResponseBody>()
    val body: LiveData<ResponseBody> get() = _body
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    suspend fun getNote() {
        withContext(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                val response = apiService.getNote()
                _note.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
            _isLoading.postValue(false)
        }
    }

    suspend fun downloadNote() {
        withContext(Dispatchers.IO) {
            _isLoading.postValue(true)
            try {
                val response = apiService.downloadNote()
                _body.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
            _isLoading.postValue(false)
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