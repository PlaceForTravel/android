package com.easyhz.placeapp.domain.repository.gallery

import com.easyhz.placeapp.domain.model.gallery.Gallery

interface ImageRepository {

    fun fetchImages(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ) : MutableList<Gallery>
}