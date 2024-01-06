package com.easyhz.placeapp.di

import com.easyhz.placeapp.api.PlaceService
import com.easyhz.placeapp.helper.Constants.PLACE_API_BASE_URL
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
object PlaceModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PlaceRetrofit

    @Provides
    @Singleton
    fun providePlaceService(
        @PlaceRetrofit retrofit: Retrofit
    ) : PlaceService = retrofit.create(PlaceService::class.java)


    @Provides
    @Singleton
    @PlaceRetrofit
    fun providePlaceRetrofit(
        @CommonModule.InterceptorOkhttpClient client: OkHttpClient,
        @CommonModule.ProvideGson gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(PLACE_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}