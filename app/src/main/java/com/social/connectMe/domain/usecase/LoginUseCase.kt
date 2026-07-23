package com.social.connectMe.domain.usecase

import com.social.connectMe.domain.model.User
import com.social.connectMe.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email and password cannot be empty"))
        }
        
        val result = repository.login(email, password)
        
        if (result.isSuccess) {
            result.getOrNull()?.let { user ->
                repository.saveUser(user)
            }
        }
        
        return result
    }
}
