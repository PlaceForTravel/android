package com.easyhz.placeapp.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.easyhz.placeapp.domain.model.search.SearchPreferences
import kotlinx.coroutines.flow.Flow

interface SearchDataStoreRepository {

    suspend fun getSearchPreferences() : Flow<SearchPreferences>

    suspend fun updateSearchPreferences(keyword: String)

    suspend fun deleteSearchKeyword(keyword: String)

    suspend fun mapSearchPreferences(preferences: Preferences) : SearchPreferences
}