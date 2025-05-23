package hn.single.server.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkStatusTracker @Inject constructor(private val context: Context) {

    private val connectivityManager =
        /*context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager*/
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)
    val networkStatus = callbackFlow {
        val callBack = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onLost(network: android.net.Network) {
                super.onLost(network)
                trySend(false)
            }
        }
        val request = NetworkRequest.Builder().build()
        connectivityManager?.registerNetworkCallback(request, callBack)
        trySend(isConnected())
        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callBack)
        }
    }.distinctUntilChanged()

    private fun isConnected(): Boolean {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val activeNetwork = connectivityManager?.activeNetwork ?: return false
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }

            else -> {
                // Use depreciated methods only on older devices
                val activeNetworkInfo = connectivityManager?.activeNetworkInfo ?: return false
                activeNetworkInfo.isConnected
            }
        }
    }
}