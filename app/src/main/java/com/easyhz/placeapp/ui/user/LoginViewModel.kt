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
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
        UserManager.user?.let {
            userState = userState.copy(user = it)
        }
    }
    fun login(context: Context, socialType: SocialLoginType) = viewModelScope.launch {
        val loginFunction: suspend (User) -> Unit = { user ->
            val step = if (validateUserId(user.userId)) LoginSteps.SUCCESS else LoginSteps.USERNAME
            setUserState(user, step)
        }
        getFCMToken()?.let { token ->
            userState = userState.update(fcmToken = token)
        }

        val social = when (socialType) {
            SocialLoginType.NAVER -> Naver
            SocialLoginType.KAKAO -> Kakao
            else -> null
        }

        social?.apply {
            setNickname(userState.user.nickname)
            setFCMToken(userState.user.fcmToken)
            login(context, loginFunction)
        }
    }

    fun logout(social: SocialLoginType) = viewModelScope.launch {
        when(social) {
            SocialLoginType.NAVER -> { Naver.logout() }
            SocialLoginType.KAKAO -> { Kakao.logout() }
            else -> {}
        }
        dataStoreRepository.deleteUserInfo()
        userState = userState.update(fcmToken = null)
        UserManager.clearUser()
        onSuccess()
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
                    userState.user.fcmToken?.let {
                        updateUserInfo()
                        UserManager.setUser(userState.user)
                    }
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
            onSuccess()
        }
    }

    private suspend fun getFCMToken(): String? = try {
        FirebaseMessaging.getInstance().token.await()
    } catch (e: Exception) {
        null
    }

    companion object {
        val TAG = this::class.java.simpleName
        const val NUM_FROM = 1
        const val NUM_UNTIL = 100_000
        const val REGEX = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣0-9]{3,8}$"
    }
}