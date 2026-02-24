package com.example.rincon_verde2.data.repository.impl

import com.example.rincon_verde2.data.local.dao.UserDao
import com.example.rincon_verde2.data.local.entity.UserEntity
import com.example.rincon_verde2.data.remote.ApiService
import com.example.rincon_verde2.data.remote.dto.LoginResponse
import com.example.rincon_verde2.data.remote.dto.LoginRequest
import com.example.rincon_verde2.data.remote.dto.RegisterRequest
import com.example.rincon_verde2.data.remote.dto.UserDto
import com.example.rincon_verde2.data.repository.UserRepository
import com.example.rincon_verde2.domain.model.User
import com.example.rincon_verde2.data.local.TokenManager
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val tokenManager: TokenManager
) : UserRepository {

    /**
     * Login user with email and password
     * Attempts API call first, then fallback to local database
     */
    override suspend fun login(email: String, password: String): Result<User> = try {
        // Call real API
        println("DEBUG: Attempting login with email=$email")
        val request = LoginRequest(email = email, password = password)
        println("DEBUG: LoginRequest created: $request")
        val response: LoginResponse = apiService.login(request)
        println("DEBUG: API Response received: $response")
        println("DEBUG: Response success=${response.success}, user=${response.user}, token=${response.token}, message=${response.message}")

        // Check if response is valid (has user and token) OR if success is explicitly true
        if (response.isValid() || (response.success && response.user != null)) {
            // Persist user locally
            val userEntity = response.user!!.toEntity()
            userDao.insertUser(userEntity)
            response.token?.let { tokenManager.saveToken(it) }

            Result.success(response.user.toDomain())
        } else {
            Result.failure(Exception(response.message ?: "Login failed"))
        }
    } catch (e: Exception) {
        println("DEBUG: API login failed with exception: ${e.javaClass.simpleName}: ${e.message}")
        e.printStackTrace()
        // Fallback: try to get user from local database
        try {
            val localUser = userDao.getUserByEmail(email)
            if (localUser != null) {
                Result.success(localUser.toDomain())
            } else {
                Result.failure(Exception("User not found locally: ${e.message}"))
            }
        } catch (fallbackError: Exception) {
            Result.failure(Exception("Login failed: ${e.message}"))
        }
    }

    /**
     * Register new user
     */
    override suspend fun register(
        email: String,
        password: String,
        displayName: String
    ): Result<User> = try {
        println("DEBUG: Attempting register with email=$email, name=$displayName")
        val request = RegisterRequest(
            name = displayName,
            email = email,
            password = password,
            passwordConfirmation = password
        )
        println("DEBUG: RegisterRequest created: $request")

        val response = apiService.register(request)
        println("DEBUG: Register API Response received: $response")
        println("DEBUG: Response success=${response.success}, user=${response.user}, token=${response.token}, message=${response.message}")

        // Check if response is valid (has user and token) OR if success is explicitly true
        if (response.isValid() || (response.success && response.user != null)) {
            val userEntity = response.user!!.toEntity()
            userDao.insertUser(userEntity)
            response.token?.let { tokenManager.saveToken(it) }
            println("DEBUG: Registration successful for user: ${response.user.email}")
            Result.success(response.user.toDomain())
        } else {
            val errorMsg = response.message ?: "Registration failed - no user data in response"
            println("DEBUG: Registration failed: $errorMsg")
            Result.failure(Exception(errorMsg))
        }
    } catch (e: Exception) {
        println("DEBUG: API register failed with exception: ${e.javaClass.simpleName}: ${e.message}")
        e.printStackTrace()
        Result.failure(Exception("Registration failed: ${e.message}"))
    }

    /**
     * Logout user
     */
    override suspend fun logout(): Result<Unit> = try {
        try {
            apiService.logout()
        } catch (_: Exception) {
            // ignore api logout failures
        }

        // Clear local database
        userDao.deleteAllUsers()
        tokenManager.clearToken()

        Result.success(Unit)
    } catch (e: Exception) {
        // Even if local clearing fails, still return success
        Result.success(Unit)
    }

    /**
     * Get current user profile from local database
     */
    override suspend fun getCurrentUser(): Result<User?> = try {
        // Return any stored user (single-user devices) as current user
        val user = userDao.getAnyUser()
        Result.success(user?.toDomain())
    } catch (e: Exception) {
        Result.failure(Exception("Failed to get current user: ${e.message}"))
    }

    /**
     * Update user profile
     */
    override suspend fun updateProfile(user: User): Result<User> = try {
        // Update local database (API update not implemented)
        val userEntity = user.toEntity()
        userDao.updateUser(userEntity)

        Result.success(user)
    } catch (e: Exception) {
        Result.failure(Exception("Failed to update profile: ${e.message}"))
    }

    // Mapper extensions
    private fun UserDto.toEntity(): UserEntity = UserEntity(
        id = id.toString(),
        email = email,
        displayName = displayName,
        favoriteCount = favoriteCount,
        reviewCount = reviewCount,
        lastSyncTimestamp = System.currentTimeMillis()
    )

    private fun UserDto.toDomain(): User = User(
        id = id.toString(),
        email = email,
        displayName = displayName,
        favoriteCount = favoriteCount,
        reviewCount = reviewCount
    )

    private fun UserEntity.toDomain(): User = User(
        id = id,
        email = email,
        displayName = displayName,
        favoriteCount = favoriteCount,
        reviewCount = reviewCount
    )

    private fun User.toEntity(): UserEntity = UserEntity(
        id = id,
        email = email,
        displayName = displayName,
        favoriteCount = favoriteCount,
        reviewCount = reviewCount,
        lastSyncTimestamp = System.currentTimeMillis()
    )
}
