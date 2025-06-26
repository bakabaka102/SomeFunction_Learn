package hn.single.server.network

import hn.single.server.common.UIState
import hn.single.server.data.network.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    internetRepo: InternetAvailabilityRepository,
    apiCall: suspend () -> T
): ApiResult<T> {
    return if (!internetRepo.isConnected.value) {
        ApiResult.Error("No internet connection", null)
    } else {
        try {
            ApiResult.Success(apiCall())
        } catch (e: Exception) {
            ApiResult.Error(e.message.toString(), null)
        }
    }
}

suspend fun <T> genericApiCallN(
    internetRepo: InternetAvailabilityRepository,
    apiCall: suspend () -> T,
): UIState<T> {
    return when (val result = safeApiCall(internetRepo, apiCall)) {
        is ApiResult.Success -> UIState.Success(result.data)
        is  ApiResult.Error -> UIState.Failure(Throwable(result.message))
    }
}

suspend fun <T> genericApiCall(
    internetRepo: InternetAvailabilityRepository,
    apiCall: suspend () -> T
): UIState<T> = withContext(Dispatchers.IO) {
    when (val result = safeApiCall(internetRepo, apiCall)) {
        is ApiResult.Success -> UIState.Success(result.data)
        is ApiResult.Error -> UIState.Failure(Throwable(result.message))
    }
}