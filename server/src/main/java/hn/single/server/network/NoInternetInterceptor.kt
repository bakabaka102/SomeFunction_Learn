package hn.single.server.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NoInternetInterceptor(
    private val internetRepo: InternetAvailabilityRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!internetRepo.isConnected.value) {
            throw IOException("No internet connection")
        }
        return chain.proceed(chain.request())
    }
}