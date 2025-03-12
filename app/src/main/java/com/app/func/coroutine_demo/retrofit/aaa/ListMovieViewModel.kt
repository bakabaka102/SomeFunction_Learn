package com.app.func.coroutine_demo.retrofit.aaa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ListMovieViewModel(private val repository: DataRepository) : ViewModel() {

    val errorMovie : LiveData<String> get() = repository.errorMovie
    val movieList get() = repository.allMovies
    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    fun getAllMovies() {
        viewModelScope.launch {
            _loading.postValue(true)
            repository.getAllMovies().apply {
                _loading.postValue(false)
            }
        }
    }
}
