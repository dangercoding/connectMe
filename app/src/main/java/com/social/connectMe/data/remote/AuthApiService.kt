package com.social.connectMe.data.remote

import com.social.connectMe.data.remote.dto.LoginRequest
import com.social.connectMe.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): UserDto

    companion object {
        const val BASE_URL = "https://api.example.com/"
    }
}
