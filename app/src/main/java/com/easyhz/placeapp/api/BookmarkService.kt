package com.easyhz.placeapp.api

import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.user.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface BookmarkService {
    /* 저장한 글 조회 */
    @POST("/user/savedBoard/list")
    suspend fun getSavedPost(
        @Query("page") page: Int,
        @Query("userId") userId: String
    ): Response<Feed>
}