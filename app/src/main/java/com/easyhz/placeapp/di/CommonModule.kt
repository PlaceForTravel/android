package com.easyhz.placeapp.di

import com.easyhz.placeapp.BuildConfig
import com.easyhz.placeapp.api.network.ServiceInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class InterceptorOkhttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ProvideGson


    @Provides
    @Singleton
    @InterceptorOkhttpClient
    fun provideMapInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ServiceInterceptor(clientId = BuildConfig.NAVER_API_CLIENT_ID, clientSecret = BuildConfig.NAVER_API_CLIENT_SECRET))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @ProvideGson
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

}