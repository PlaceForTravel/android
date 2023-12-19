package com.easyhz.placeapp.domain.repository.search

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.easyhz.placeapp.di.CommonModule.ProvideGson
import com.easyhz.placeapp.domain.model.search.SearchPreferences
import com.easyhz.placeapp.domain.repository.search.SearchDataStoreRepositoryImpl.PreferencesKeys.KEYWORD
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SearchDataStoreRepositoryImpl
@Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ProvideGson private val gson: Gson
): SearchDataStoreRepository {

    private object PreferencesKeys {
        val KEYWORD = stringPreferencesKey("keyword")
    }
    override suspend fun getSearchPreferences(): Flow<SearchPreferences> =
        dataStore.data.catch { e ->
            handleError(e)
        }.map { preferences ->
            mapSearchPreferences(preferences)
        }

    override suspend fun updateSearchPreferences(keyword: String) {
        dataStore.edit { preferences ->
            val currentSearchPreferences = mapSearchPreferences(preferences)
            currentSearchPreferences.keyword.remove(keyword)
            currentSearchPreferences.keyword.addFirst(keyword)

            if (currentSearchPreferences.keyword.size > MAX_SEARCH_HISTORY) currentSearchPreferences.keyword.removeLastOrNull()

            preferences[KEYWORD] = gson.toJson(currentSearchPreferences)
        }
    }

    override suspend fun deleteSearchKeyword(keyword: String) {
        dataStore.edit { preferences ->
            val currentSearchPreferences = mapSearchPreferences(preferences)
            currentSearchPreferences.keyword.remove(keyword)
            preferences[KEYWORD] = gson.toJson(currentSearchPreferences)
        }
    }

    private fun mapSearchPreferences(preferences: Preferences): SearchPreferences =
        (preferences[KEYWORD] ?: "{}").fromJson()

    private suspend fun FlowCollector<Preferences>.handleError(e: Throwable) {
        if (e is IOException) {
            Log.d(this::class.java.simpleName, e.toString())
            emit(emptyPreferences())
        } else {
            throw e
        }
    }

    private inline fun <reified T> String?.fromJson(): T =
        this?.let { gson.fromJson(it, T::class.java) } ?: T::class.java.getDeclaredConstructor().newInstance()

    companion object {
        const val MAX_SEARCH_HISTORY = 10
    }
}