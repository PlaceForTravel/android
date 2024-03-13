package com.easyhz.placeapp.domain.repository.user

import com.easyhz.placeapp.domain.model.user.User
import retrofit2.Response

interface UserRepository {
    suspend fun login(
        user: User,
        onComplete: (Boolean) -> Unit
    )

    suspend fun validateUserId(userId: String): Response<String>

    suspend fun validateNickname(nickname: String): Response<Boolean>
}