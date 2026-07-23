package com.social.connectMe.data.remote.dto

import com.social.connectMe.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    var id: String,
    var email: String,
    var name: String,
    var token: String
)

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        name = name,
        token = token
    )
}
