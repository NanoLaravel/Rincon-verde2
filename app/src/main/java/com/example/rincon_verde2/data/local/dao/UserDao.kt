package com.example.rincon_verde2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rincon_verde2.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Insert a user into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    /**
     * Get current user by ID
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    /**
     * Get current user as Flow for reactive updates
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: String): Flow<UserEntity?>

    /**
     * Get any user (first) stored locally. Useful for retrieving current user when
     * only one user is stored on device.
     */
    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getAnyUser(): UserEntity?

    /**
     * Update user information
     */
    @Update
    suspend fun updateUser(user: UserEntity)

    /**
     * Delete user
     */
    @Delete
    suspend fun deleteUser(user: UserEntity)

    /**
     * Clear all users (logout)
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    /**
     * Get count of stored users (for checking login state)
     */
    @Query("SELECT COUNT(*) FROM users")
    fun getUserCount(): Flow<Int>

    /**
     * Get user by email
     */
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    /**
     * Check if user exists
     */
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE id = :userId)")
    suspend fun userExists(userId: String): Boolean
}
