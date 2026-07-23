package com.social.connectMe.domain.repository

import com.social.connectMe.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun saveUser(user: User)
    suspend fun clearUser()
    val isLoggedIn: Flow<Boolean>
}
