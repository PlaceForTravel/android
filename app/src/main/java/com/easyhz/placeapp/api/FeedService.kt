package com.easyhz.placeapp.api

import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.UserInfo
import com.easyhz.placeapp.domain.model.feed.comment.Comment
import com.easyhz.placeapp.domain.model.feed.comment.write.PostComment
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedService {

    /* 전체 글 조회 */
    @GET("/board/list")
    suspend fun getFeed(
        @Query("page") page: Int
    ) : Response<Feed>

    /* 글 상세 조회 */
    @GET("/board/detail/{boardId}")
    suspend fun getFeedDetail(
        @Path("boardId") id: Int
    ) : Response<FeedDetail>

    /* 댓글 조회 */
    @GET("/board/{boardId}/comment")
    suspend fun getComments(
        @Path("boardId") id: Int,
        @Query("page") page: Int
    ) : Response<Comment>

    /* 댓글 등록 */
    @POST("/board/{boardId}/comment/save")
    suspend fun writeComments(
        @Path("boardId") id: Int,
        @Body comment: PostComment
    ) : Response<Void>

    /* 글 등록 -> 헤더에 토큰 TODO: 파라미터 수정 필요 */
    @POST("/board/save")
    suspend fun writePost()

    @DELETE("/board/delete/{boardId}")
    suspend fun deletePost(
        @Path("boardId") id: Int
    )

    /* 글 저장 TODO: userId 와 토큰 필요 (로그인 구현 시 추가)*/
    @POST("/board/like/{boardId}")
    suspend fun savePost(
        @Path("boardId") id: Int,
        @Body userInfo: UserInfo
    ) : Response<Void>

    /* 장소 저장 */
    @POST("/board/saveBoardPlace/{boardPlaceId}")
    suspend fun savePlace(
        @Path("boardPlaceId") id: Int
    )



}