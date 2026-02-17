package com.example.rincon_verde2.data.repository

import com.example.rincon_verde2.domain.model.User

interface UserRepository {
    /**
     * Login user with email and password
     */
    suspend fun login(email: String, password: String): Result<User>

    /**
     * Register new user
     */
    suspend fun register(email: String, password: String, displayName: String): Result<User>

    /**
     * Logout user
     */
    suspend fun logout(): Result<Unit>

    /**
     * Get current user profile
     */
    suspend fun getCurrentUser(): Result<User?>

    /**
     * Update user profile
     */
    suspend fun updateProfile(user: User): Result<User>
}
