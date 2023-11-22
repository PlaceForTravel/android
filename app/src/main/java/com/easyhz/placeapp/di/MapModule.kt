package com.easyhz.placeapp.di

import com.easyhz.placeapp.api.MapService
import com.easyhz.placeapp.helper.Constants.MAP_API_BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier


@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MapRetrofit

    @Provides
    @Singleton
    fun provideMapService(
        @MapRetrofit retrofit: Retrofit
    ) : MapService = retrofit.create(MapService::class.java)


    @Provides
    @Singleton
    @MapRetrofit
    fun provideMapRetrofit(
        @CommonModule.InterceptorOkhttpClient client: OkHttpClient,
        @CommonModule.ProvideGson gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(MAP_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}