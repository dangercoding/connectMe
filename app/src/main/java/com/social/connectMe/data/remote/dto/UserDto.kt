package com.social.connectMe.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int? = null,
    val authId: String? = null,
    val source: String? = null,
    val slug: String? = null,
    val username: String? = null,
    val nicename: String? = null,
    val email: String? = null,
    val password: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null,
    @SerialName("refresh_token_expires_at")
    val refreshTokenExpiresAt: String? = null,
    val registered: String? = null,
    val displayname: String? = null,
    val nickname: String? = null,
    val description: String? = null,
    val designation: String? = null,
    val city: String? = null,
    val country: String? = null,
    val capabilities: String? = null,
    @SerialName("user_group")
    val userGroup: String? = null,
    val avatar: String? = null,
    val cover: String? = null,
    val points: Int? = null,
    val followers: Int? = null,
    val questions: Int? = null,
    val answers: Int? = null,
    @SerialName("best_answers")
    val bestAnswers: Int? = null,
    val posts: Int? = null,
    val comments: Int? = null,
    val notifications: Int? = null,
    @SerialName("new_notifications")
    val newNotifications: Int? = null,
    val verified: Int? = null,
    val admin: Int? = null,
    val status: Int? = null,
    @SerialName("account_status")
    val accountStatus: String? = null,
    @SerialName("scheduled_delete_at")
    val scheduledDeleteAt: String? = null,
    @SerialName("deactivated_at")
    val deactivatedAt: String? = null,
    @SerialName("reactivate_at")
    val reactivateAt: String? = null,
    @SerialName("deleted_at")
    val deletedAt: String? = null,
    @SerialName("badge_id")
    val badgeId: String? = null,
    @SerialName("device_token")
    val deviceToken: String? = null,
    @SerialName("profile_crediential")
    val profileCredential: String? = null,
    @SerialName("remember_token")
    val rememberToken: String? = null,
    @SerialName("email_verified_at")
    val emailVerifiedAt: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    val role: Int? = null,
    val badge: BadgeDto? = null
)

@Serializable
data class BadgeDto(
    @SerialName("user_id")
    val userId: String? = null,
    val name: String? = null,
    val color: String? = null
)
