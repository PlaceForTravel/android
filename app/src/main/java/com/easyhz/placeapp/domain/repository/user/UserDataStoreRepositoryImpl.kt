package com.easyhz.placeapp.domain.repository.user

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepositoryImpl.PreferenceKeys.IS_FIRST_RUN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class UserDataStoreRepositoryImpl
@Inject constructor(
    private val dataStore: DataStore<Preferences>
): UserDataStoreRepository {

    private object PreferenceKeys {
        val IS_FIRST_RUN = booleanPreferencesKey("isFirstRun")
    }
    override suspend fun getIsFirstRun(): Flow<Boolean> =
        dataStore.data.catch { e ->
            handleError(e)
        }.map { preferences ->
            preferences[IS_FIRST_RUN] ?: true
        }

    private suspend fun FlowCollector<Preferences>.handleError(e: Throwable) {
        if (e is IOException) {
            Log.d(this::class.java.simpleName, e.toString())
            emit(emptyPreferences())
        } else {
            throw e
        }
    }

}