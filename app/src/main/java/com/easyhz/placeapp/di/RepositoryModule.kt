package com.easyhz.placeapp.di

import com.easyhz.placeapp.domain.repository.search.SearchDataStoreRepository
import com.easyhz.placeapp.domain.repository.search.SearchDataStoreRepositoryImpl
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import com.easyhz.placeapp.domain.repository.feed.FeedRepositoryImpl
import com.easyhz.placeapp.domain.repository.place.PlaceRepository
import com.easyhz.placeapp.domain.repository.place.PlaceRepositoryImpl
import com.easyhz.placeapp.domain.repository.gallery.ImageRepository
import com.easyhz.placeapp.domain.repository.gallery.ImageRepositoryImpl
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepository
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl,
    ): ImageRepository

    @Binds
    @ViewModelScoped
    abstract fun bindPlaceRepository(
        placeRepositoryImpl: PlaceRepositoryImpl,
    ): PlaceRepository

    @Binds
    @ViewModelScoped
    abstract fun bindFeedRepository(
        feedRepositoryImpl: FeedRepositoryImpl,
    ): FeedRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSearchDataStoreRepository(
        dataStoreRepositoryImpl: SearchDataStoreRepositoryImpl,
    ): SearchDataStoreRepository

    @Binds
    @ViewModelScoped
    abstract fun bindUserDataStoreRepository(
        dataStoreRepositoryImpl: UserDataStoreRepositoryImpl,
    ): UserDataStoreRepository
}