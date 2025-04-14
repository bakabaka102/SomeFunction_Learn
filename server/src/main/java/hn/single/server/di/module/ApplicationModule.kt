package hn.single.server.di.module

/*import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hn.single.server.BuildConfig
import hn.single.server.common.Const
import hn.single.server.data.network.ApiInterface
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY // LOG TOÀN BỘ request/response
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }


    @Singleton
    @Provides
    fun provideNetworkService(okHttpClient: OkHttpClient): ApiInterface {

        val contentType = "application/json; charset=UTF8".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
        }
        val factory = json.asConverterFactory(contentType)
        return Retrofit
            .Builder()
            .baseUrl(Const.BASE_URL)
            *//*.addConverterFactory(GsonConverterFactory.create())*//*
            .addConverterFactory(factory)
            .client(okHttpClient) // <-- Gắn OkHttp có logging
            .build()
            .create(ApiInterface::class.java)
    }
}*/
