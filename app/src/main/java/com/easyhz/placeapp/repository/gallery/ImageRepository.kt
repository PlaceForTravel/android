package com.easyhz.placeapp.repository.gallery

import com.easyhz.placeapp.gallery.Gallery

interface ImageRepository {

    fun fetchImages(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ) : MutableList<Gallery>
}