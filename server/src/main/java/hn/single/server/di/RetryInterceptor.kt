package hn.single.server.di

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(
    private val maxRetry: Int = 3,
    private val delayMillis: Long = 1000,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var tryCount = 0
        var response: Response

        val request = chain.request()
        while (true) {
            try {
                response = chain.proceed(request)
                if (response.isSuccessful || tryCount >= maxRetry) return response
            } catch (e: IOException) {
                if (tryCount >= maxRetry) throw e
            }

            tryCount++
            Thread.sleep(delayMillis)
        }
    }
}
