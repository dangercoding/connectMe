package com.social.connectMe.data.remote.dto

import com.social.connectMe.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("expires_in")
    val expiresIn: String? = null,
    val token: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
    val refreshToken: String? = null,
    @SerialName("refreshTokenExpiresIn")
    val refreshTokenExpiresIn: String? = null,
    val user: UserDto? = null
)

fun LoginResponseDto.toDomain(): User {
    return User(
        id = user?.id?.toString() ?: "",
        email = user?.email ?: "",
        name = user?.displayname ?: user?.username ?: "",
        token = token ?: ""
    )
}
