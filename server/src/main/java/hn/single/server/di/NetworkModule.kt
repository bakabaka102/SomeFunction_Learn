package hn.single.server.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hn.single.server.BuildConfig
import hn.single.server.common.Const
import hn.single.server.data.network.ApiInterface
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthInterceptor(): Interceptor {
        return AuthInterceptor {
            /*TODO: Replace with real token (e.g., from SharedPreferences / DataStore)
            "your_access_token_here"*/
            BuildConfig.API_KEY
        }
    }

    @Provides
    @Singleton
    @Named("retry")
    fun provideRetryInterceptor(): Interceptor = RetryInterceptor()

    @Provides
    @Singleton
    @Named("timing")
    fun provideTimingInterceptor(): Interceptor = ApiTimingInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("auth") authInterceptor: Interceptor,
        @Named("retry") retryInterceptor: Interceptor,
        @Named("timing") timingInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(retryInterceptor)
            .addInterceptor(timingInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(okHttpClient: OkHttpClient): ApiInterface {
        val contentType = "application/json; charset=UTF8".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        val factory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            /*.addConverterFactory(GsonConverterFactory.create())*/
            .addConverterFactory(factory)
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)
    }
}
