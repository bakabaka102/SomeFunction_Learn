package hn.single.server.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiTimingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val start = System.nanoTime()
        val response = chain.proceed(request)
        val end = System.nanoTime()

        val durationMs = (end - start) / 1_000_000.0
        Log.d("API-TIMING", "${request.method} ${request.url} took $durationMs ms")

        return response
    }
}
