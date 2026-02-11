package com.example.rincon_verde2.domain.model

data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val profileImageUrl: String? = null,
    val favoriteCount: Int = 0,
    val reviewCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

data class AuthCredentials(
    val email: String,
    val password: String
)

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String, val errorCode: Int = -1) : AuthResult()
    object Loading : AuthResult()
}

sealed class AuthState {
    object Unauthenticated : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
    object Loading : AuthState()
}
