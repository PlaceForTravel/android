package com.easyhz.placeapp.api

import com.easyhz.placeapp.domain.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/user/login")
    suspend fun login(
        user: User
    ) : Response<Void>

    @GET("/user/nickname")
    suspend fun validateNickname(
        @Query("nickname") nickname: String
    ) : Response<Boolean>
}