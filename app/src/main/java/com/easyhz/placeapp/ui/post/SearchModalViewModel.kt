package com.easyhz.placeapp.ui.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchModalViewModel: ViewModel() {

    private val _searchValue = mutableStateOf("")
    val searchValue: State<String>
        get() = _searchValue


    private val _searchActive = mutableStateOf(false)
    val searchActive: State<Boolean>
        get() = _searchActive


    fun setSearchValue(value: String) {
        _searchValue.value = value
    }

    fun setSearchActive(active: Boolean) {
        _searchActive.value = active
    }
}