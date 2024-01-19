package com.easyhz.placeapp.ui.user

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.model.user.LoginSteps
import com.easyhz.placeapp.domain.model.user.UserManager
import com.easyhz.placeapp.domain.model.user.UserState
import com.easyhz.placeapp.domain.model.user.update
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepository
import com.easyhz.placeapp.domain.repository.user.UserRepository
import com.easyhz.placeapp.domain.repository.user.social.Naver
import com.easyhz.placeapp.domain.model.user.User
import com.easyhz.placeapp.domain.repository.user.social.Kakao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val dataStoreRepository: UserDataStoreRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private var debounceJob: Job? = null
    private val regex = Regex(REGEX)

    var userState by mutableStateOf(UserState())

    init {
        Log.d(TAG, "user: ${UserManager.user}")
    }
    fun login(context: Context, social: SocialLoginType) {
        val loginFunction: suspend (User) -> Unit = { user ->
            val step = if (validateUserId(user.userId)) LoginSteps.SUCCESS else LoginSteps.USERNAME
            setUserState(user, step)
        }

        when (social) {
            SocialLoginType.NAVER -> {
                Naver.setNickname(userState.user.nickname)
                Naver.login(context, loginFunction)
            }
            SocialLoginType.KAKAO -> {
                Kakao.setNickname(userState.user.nickname)
                Kakao.login(context, loginFunction)
            }
            else -> {}
        }
    }

    fun logout(social: SocialLoginType) = viewModelScope.launch {
        when(social) {
            SocialLoginType.NAVER -> { Naver.logout() }
            SocialLoginType.KAKAO -> { Kakao.logout() }
            else -> {}
        }
        dataStoreRepository.deleteUserInfo()
        UserManager.clearUser()
    }

    fun manageNavigateToBack(onNavigateToBack: () -> Unit) {
        when(userState.step) {
            LoginSteps.LOGIN -> onNavigateToBack()
            LoginSteps.USERNAME -> {
                setUserStateStep(LoginSteps.LOGIN)
                logout(userState.user.type)
            }
            LoginSteps.SUCCESS -> { }
        }
    }

    fun setUserStateStep(value: LoginSteps) {
        userState = userState.copy(step = value)
    }

    fun updateUserInfo() = viewModelScope.launch {
        dataStoreRepository.updateUserInfo(user = userState.user)
    }

    fun setNickname(value: String) {
        userState = userState
            .update(nickname = value)
            .copy(isValid = false, isLoading = true)

        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            if (!regex.matches(userState.user.nickname)) {
                userState = userState.copy(isValid = false)
                userState = userState.copy(isLoading = false)
                return@launch
            }
            delay(500)
            validateNickname()
        }
    }

    fun onSuccess() = viewModelScope.launch {
        try {
            userRepository.login(user = userState.user) { isSuccessful ->
                if (isSuccessful) {
                    userState = userState.copy(onSuccess = true)
                    setUserStateStep(LoginSteps.SUCCESS)
                    updateUserInfo()
                    UserManager.setUser(userState.user)
                } else {
                    userState = userState.copy(onSuccess = false)
                }
            }
        } catch (e: Exception) {
          userState = userState.copy(error = e.localizedMessage)
        }
    }

    fun initNickname() = viewModelScope.launch {
        while(!userState.isValid) {
            val num = Random.nextInt(NUM_FROM, NUM_UNTIL)
            val value = "여정이$num"
            userState = userState.update(nickname = value)
            validateNickname()
            delay(500)
        }
    }


    private suspend fun validateUserId(userId: String): Boolean = try {
        val response = userRepository.validateUserId(userId)
        response.isSuccessful && response.body() == true
    } catch (e: Exception) {
        userState = userState.copy(error = e.localizedMessage)
        false
    }

    private fun validateNickname() = viewModelScope.launch {
        try {
            val response = userRepository.validateNickname(userState.user.nickname)
            if (response.isSuccessful) {
                response.body()?.let {
                    userState = userState.copy(isValid = !it)
                }
            }
        } catch (e: Exception) {
            userState = userState.copy(error = e.localizedMessage)
        } finally {
            userState = userState.copy(isLoading = false)
        }
    }

    private fun setUserState(user: User, step: LoginSteps) {
        userState = userState.copy(
                user = user,
                step = step
            )
        if (step == LoginSteps.SUCCESS) {
            updateUserInfo()
            UserManager.setUser(userState.user)
        }
    }

    companion object {
        val TAG = this::class.java.simpleName
        const val NUM_FROM = 1
        const val NUM_UNTIL = 100_000
        const val REGEX = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣0-9]{3,8}$"
    }
}