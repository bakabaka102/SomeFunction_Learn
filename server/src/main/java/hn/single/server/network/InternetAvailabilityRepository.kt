package hn.single.server.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InternetAvailabilityRepository @Inject constructor(networkStatusTracker: NetworkStatusTracker) {

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    init {
        CoroutineScope(Dispatchers.Default).launch {
            networkStatusTracker.networkStatus.collect { isConnected ->
                _isConnected.value = isConnected
            }
        }
    }
}