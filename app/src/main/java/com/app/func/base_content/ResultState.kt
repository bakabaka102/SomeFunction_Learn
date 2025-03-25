package com.app.func.base_content

sealed class ResultState<out T> {

    //data object Loading : UiState<Nothing>()
    data class Loading(val isLoading: Boolean = false) : ResultState<Nothing>()

    data class Success<T>(val data: T) : ResultState<T>()

    data class Error(val message: String) : ResultState<Nothing>()

}