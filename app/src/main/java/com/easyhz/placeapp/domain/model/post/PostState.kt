package com.easyhz.placeapp.domain.model.post

data class PostState(
    val post: Post = Post(),
    val unSelected: List<Int> = emptyList(),
    val isEqualCity: Boolean = false,
    val onSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

fun PostState.update(
    content: String = post.content,
    cityName: String = post.cityName,
    userId: String = post.userId,
    nickname: String = post.nickname,
    places: List<Place> = post.places
): PostState {
    val post = post.copy(
        content = content,
        cityName = cityName,
        userId = userId,
        nickname = nickname,
        places = places
    )
    return copy(post = post)
}