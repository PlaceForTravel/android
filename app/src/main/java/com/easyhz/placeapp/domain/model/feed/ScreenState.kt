package com.easyhz.placeapp.domain.model.feed

data class ScreenState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
