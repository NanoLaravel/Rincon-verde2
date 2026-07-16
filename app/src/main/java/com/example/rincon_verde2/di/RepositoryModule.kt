package com.example.rincon_verde2.di

import com.example.rincon_verde2.data.repository.EventRepository
import com.example.rincon_verde2.data.repository.PlaceRepository
import com.example.rincon_verde2.data.repository.ProductRepository
import com.example.rincon_verde2.data.repository.UserRepository
import com.example.rincon_verde2.data.repository.impl.EventRepositoryImpl
import com.example.rincon_verde2.data.repository.impl.PlaceRepositoryImpl
import com.example.rincon_verde2.data.repository.impl.ProductRepositoryImpl
import com.example.rincon_verde2.data.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlaceRepository(
        impl: PlaceRepositoryImpl
    ): PlaceRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        impl: EventRepositoryImpl
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}
