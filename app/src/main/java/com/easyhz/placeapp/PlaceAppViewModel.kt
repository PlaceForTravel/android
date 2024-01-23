package com.easyhz.placeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.model.user.UserManager
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceAppViewModel
@Inject constructor(
    private val dataStoreRepository: UserDataStoreRepository
): ViewModel() {
    private val TAG = this::class.java.simpleName
    init {
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch {
        dataStoreRepository.getUserInfo().collectLatest {
            println("user>>>>>>>> $it")
            if (it.fcmToken != null) {
                UserManager.setUser(it)
            }
        }
    }
}