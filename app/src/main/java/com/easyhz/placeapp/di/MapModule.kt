package com.easyhz.placeapp.di

import com.easyhz.placeapp.BuildConfig
import com.easyhz.placeapp.api.MapService
import com.easyhz.placeapp.api.network.ServiceInterceptor
import com.easyhz.placeapp.helper.Constants.MAP_API_BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InterceptorOkhttpClient
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MapRetrofit
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ProvideGson


    @Provides
    @Singleton
    fun provideMapService(
        @MapRetrofit retrofit: Retrofit
    ) : MapService = retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    @InterceptorOkhttpClient
    fun provideMapInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ServiceInterceptor(clientId = BuildConfig.NAVER_MAP_API_CLIENT_ID, clientSecret = BuildConfig.NAVER_MAP_API_CLIENT_SECRET))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }


    @Provides
    @ProvideGson
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    @MapRetrofit
    fun provideMapRetrofit(
        @InterceptorOkhttpClient client: OkHttpClient,
        @ProvideGson gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(MAP_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}