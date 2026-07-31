package com.social.connectMe.data.remote

import com.social.connectMe.data.remote.dto.LoginRequest
import com.social.connectMe.data.remote.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers("No-Authentication: true")
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponseDto
}
