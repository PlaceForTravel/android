package com.easyhz.placeapp.api

import com.easyhz.placeapp.domain.model.feed.Feed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookmarkService {
    /* 저장한 글 조회 */
    @GET("/user/savedBoard/list")
    suspend fun getSavedPost(
        @Query("page") page: Int,
        @Query("userId") userId: String
    ): Response<Feed>

    @GET("/user/savedBoardPlace/city")
    suspend fun getCity(
        @Query("userId") userId: String
    ): Response<List<String>>
}