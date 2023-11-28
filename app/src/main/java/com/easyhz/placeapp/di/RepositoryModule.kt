package com.easyhz.placeapp.di

import com.easyhz.placeapp.domain.repository.PlaceRepository
import com.easyhz.placeapp.domain.repository.PlaceRepositoryImpl
import com.easyhz.placeapp.domain.repository.gallery.ImageRepository
import com.easyhz.placeapp.domain.repository.gallery.ImageRepositoryImpl
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
}