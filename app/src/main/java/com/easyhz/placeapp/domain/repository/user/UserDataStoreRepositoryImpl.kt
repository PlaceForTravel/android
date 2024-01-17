package com.easyhz.placeapp.domain.repository.user

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easyhz.placeapp.di.CommonModule.ProvideGson
import com.easyhz.placeapp.domain.model.user.User
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepositoryImpl.PreferenceKeys.IS_FIRST_RUN
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepositoryImpl.PreferenceKeys.USER_INFO
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


class UserDataStoreRepositoryImpl
@Inject constructor(
    @Named("userPreferences") private val dataStore: DataStore<Preferences>,
    @ProvideGson private val gson: Gson
): UserDataStoreRepository {

    private object PreferenceKeys {
        val IS_FIRST_RUN = booleanPreferencesKey("isFirstRun")
        val USER_INFO = stringPreferencesKey("userInfo")
    }
    override suspend fun getIsFirstRun(): Flow<Boolean> =
        dataStore.data.catch { e ->
            handleError(e)
        }.map { preferences ->
            preferences[IS_FIRST_RUN] ?: true
        }

    override suspend fun getUserInfo(): Flow<User>  =
        dataStore.data.catch { e ->
            handleError(e)
        }.map { preferences ->
            mapPreferences(preferences)
        }

    override suspend fun updateUserInfo(user: User) {
        dataStore.edit { preference ->
            preference[USER_INFO] = gson.toJson(user)
        }
    }

    override suspend fun deleteUserInfo() {
        dataStore.edit { preference ->
            preference.remove(USER_INFO)
        }
    }

    private suspend fun FlowCollector<Preferences>.handleError(e: Throwable) {
        if (e is IOException) {
            Log.d(this::class.java.simpleName, e.toString())
            emit(emptyPreferences())
        } else {
            throw e
        }
    }

    private fun mapPreferences(preferences: Preferences): User =
        (preferences[USER_INFO] ?: "{}").fromJson()

    private inline fun <reified T> String?.fromJson(): T =
        this?.let { gson.fromJson(it, T::class.java) } ?: T::class.java.getDeclaredConstructor().newInstance()

}