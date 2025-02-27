package hn.single.server.di.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hn.single.server.common.Const
import hn.single.server.data.network.ApiInterface
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideNetworkService(): ApiInterface {

        val contentType = "application/json; charset=UTF8".toMediaType()
        val factory = Json.asConverterFactory(contentType)
        return Retrofit
            .Builder()
            .baseUrl(Const.BASE_URL)
            /*.addConverterFactory(GsonConverterFactory.create())*/
            .addConverterFactory(factory)
            .build()
            .create(ApiInterface::class.java)
    }
}
