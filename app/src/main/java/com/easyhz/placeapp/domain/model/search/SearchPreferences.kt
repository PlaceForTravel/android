package com.easyhz.placeapp.domain.model.search

import java.util.LinkedList

data class SearchPreferences(
    val keyword: LinkedList<String> = LinkedList()
)
