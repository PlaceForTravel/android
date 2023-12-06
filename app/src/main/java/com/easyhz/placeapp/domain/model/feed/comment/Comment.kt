package com.easyhz.placeapp.domain.model.feed.comment

import com.easyhz.placeapp.domain.model.feed.Pageable
import com.easyhz.placeapp.domain.model.feed.SortX

data class Comment(
    val content: List<CommentContent>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)