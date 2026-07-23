package com.social.connectMe.data.repository

import com.social.connectMe.data.local.datastore.UserPreferences
import com.social.connectMe.data.remote.AuthApiService
import com.social.connectMe.data.remote.dto.LoginRequest
import com.social.connectMe.data.remote.dto.toDomain
import com.social.connectMe.domain.model.User
import com.social.connectMe.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            var response = apiService.login(LoginRequest(email, password))
            response.apply {
                token="AHDGGFYEEEBF_DWUW_8873737"
                name="John Doe"
                id="123456789"
            }
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveUser(user: User) {
        userPreferences.saveUser(user)
    }

    override suspend fun clearUser() {
        userPreferences.clearUser()
    }

    override val isLoggedIn: Flow<Boolean> = userPreferences.isLoggedIn
}
