package com.example.rincon_verde2.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: Int,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val displayName: String,
    @SerialName("favoriteCount")
    val favoriteCount: Int = 0,
    @SerialName("reviewCount")
    val reviewCount: Int = 0
)

@Serializable
data class LoginRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)

@Serializable
data class RegisterRequest(
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("password_confirmation") val passwordConfirmation: String
)

@Serializable
data class SocialLoginRequest(
    @SerialName("token") val token: String,
    @SerialName("provider") val provider: String // "google" o "facebook"
)

@Serializable
data class LoginResponse(
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("user")
    val user: UserDto? = null,
    @SerialName("token")
    val token: String? = null
) {
    // Helper to check if response is valid (has user and token)
    fun isValid(): Boolean = user != null && token != null
    
    val isSuccessful: Boolean get() = success ?: isValid()
}
