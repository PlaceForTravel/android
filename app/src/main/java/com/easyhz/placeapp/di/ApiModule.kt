package com.easyhz.placeapp.di

import com.easyhz.placeapp.api.BookmarkService
import com.easyhz.placeapp.api.FeedService
import com.easyhz.placeapp.api.UserService
import com.easyhz.placeapp.helper.Constants.MAIN_API_BASE_URL
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiRetrofit

    @Provides
    @Singleton
    fun provideFeedService(
        @ApiRetrofit retrofit: Retrofit
    ) : FeedService = retrofit.create(FeedService::class.java)

    @Provides
    @Singleton
    fun provideBookmarkService(
        @ApiRetrofit retrofit: Retrofit
    ) : BookmarkService = retrofit.create(BookmarkService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        @ApiRetrofit retrofit: Retrofit
    ) : UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    @ApiRetrofit
    fun provideApiRetrofit(
        @CommonModule.InterceptorOkhttpClient client: OkHttpClient,
        @CommonModule.ProvideGson gson: Gson,
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(MAIN_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}