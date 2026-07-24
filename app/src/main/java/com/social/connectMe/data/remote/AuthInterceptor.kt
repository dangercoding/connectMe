package com.social.connectMe.data.remote

import com.social.connectMe.data.local.datastore.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferences: UserPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // Skip adding token if the request has the "No-Authentication" header
        if (request.header("No-Authentication") != null) {
            val newRequest = request.newBuilder()
                .removeHeader("No-Authentication")
                .build()
            return chain.proceed(newRequest)
        }

        val token = runBlocking {
            userPreferences.authToken.first()
        }

        val authenticatedRequest = if (!token.isNullOrEmpty()) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(authenticatedRequest)
    }
}
