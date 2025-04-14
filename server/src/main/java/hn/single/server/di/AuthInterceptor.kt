package hn.single.server.di

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = tokenProvider()

        val newRequest = if (token != null) {
            request.newBuilder()
                //.addHeader("Authorization", "Bearer $token")
                .addHeader("Authorization", token)
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}
