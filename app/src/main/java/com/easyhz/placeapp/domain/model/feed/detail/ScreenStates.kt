package com.easyhz.placeapp.domain.model.feed.detail

data class DeleteState(
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
