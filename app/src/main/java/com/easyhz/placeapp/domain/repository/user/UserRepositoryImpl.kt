package com.easyhz.placeapp.domain.repository.user

import com.easyhz.placeapp.api.UserService
import com.easyhz.placeapp.domain.model.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
    private val userService: UserService
): UserRepository {
    override suspend fun login(
        user: User,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val response = userService.login(user)
        if (response.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }

    override suspend fun validateUserId(userId: String): Response<String> = userService.validateUserId(userId)

    override suspend fun validateNickname(nickname: String): Response<Boolean> = userService.validateNickname(nickname)
}