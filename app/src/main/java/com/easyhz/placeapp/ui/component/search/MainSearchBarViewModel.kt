package com.easyhz.placeapp.ui.component.search

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.repository.search.SearchDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainSearchBarViewModel
@Inject constructor(
    private val dataStoreRepository: SearchDataStoreRepository
):ViewModel() {
    var text by mutableStateOf("")

    private val _searchHistoryList = mutableStateOf(emptyList<String>())
    val searchHistoryList: State<List<String>>
        get() = _searchHistoryList
    fun onValueChange(value: String) {
        text = value
    }

    fun onCanceled() {
        text = ""
    }

    fun getSearchHistory() = viewModelScope.launch {
        dataStoreRepository.getSearchPreferences().collectLatest {
            _searchHistoryList.value = it.keyword
        }
    }

    fun updateSearchHistory() = viewModelScope.launch {
        dataStoreRepository.updateSearchPreferences(text)
    }

    fun deleteSearchHistory(keyword: String) = viewModelScope.launch {
        dataStoreRepository.deleteSearchKeyword(keyword)
    }

}