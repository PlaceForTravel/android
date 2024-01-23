package com.easyhz.placeapp.domain.repository.user

import com.easyhz.placeapp.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun getIsFirstRun() : Flow<Boolean>

    suspend fun getUserInfo(): Flow<User>

    suspend fun updateUserInfo(user: User)

    suspend fun deleteUserInfo()
}