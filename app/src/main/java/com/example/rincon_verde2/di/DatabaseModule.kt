package com.example.rincon_verde2.di

import android.content.Context
import androidx.room.Room
import com.example.rincon_verde2.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rincon_verde_db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Singleton
    @Provides
    fun providePlaceDao(database: AppDatabase) = database.placeDao()

    @Singleton
    @Provides
    fun provideEventDao(database: AppDatabase) = database.eventDao()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()
}
