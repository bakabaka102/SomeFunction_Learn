package com.app.func.base_content

sealed class UiState<out T> {

    //data object Loading : UiState<Nothing>()
    data class Loading(val isLoading: Boolean = false) : UiState<Nothing>()

    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val message: String) : UiState<Nothing>()

}