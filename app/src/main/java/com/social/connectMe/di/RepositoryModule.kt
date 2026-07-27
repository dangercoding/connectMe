package com.social.connectMe.di

import com.social.connectMe.data.repository.AuthRepositoryImpl
import com.social.connectMe.data.repository.LocationRepositoryImpl
import com.social.connectMe.data.repository.ThemeRepositoryImpl
import com.social.connectMe.domain.repository.AuthRepository
import com.social.connectMe.domain.repository.LocationRepository
import com.social.connectMe.domain.repository.ThemeRepository
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
    abstract fun bindThemeRepository(
        themeRepositoryImpl: ThemeRepositoryImpl
    ): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
