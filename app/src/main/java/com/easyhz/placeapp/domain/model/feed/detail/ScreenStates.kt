package com.easyhz.placeapp.domain.model.feed.detail

import com.easyhz.placeapp.ui.component.detail.DetailActions

data class DetailState(
    val type: DetailActions? = null,
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)