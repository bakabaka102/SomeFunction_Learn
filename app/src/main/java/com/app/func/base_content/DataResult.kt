package com.app.func.base_content

sealed class DataResult<out T> {

    data class Success<out T>(val data: T) : DataResult<T>()

    data class Error(val throwable: Throwable) : DataResult<Nothing>()

    object Loading : DataResult<Nothing>()

}