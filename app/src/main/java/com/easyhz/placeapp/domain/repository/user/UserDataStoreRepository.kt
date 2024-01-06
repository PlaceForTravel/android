package com.easyhz.placeapp.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun getIsFirstRun() : Flow<Boolean>
}