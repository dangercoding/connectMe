package com.social.connectMe.core.di

import android.content.Context
import com.social.connectMe.data.local.datastore.ThemePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideThemePreferences(@ApplicationContext context: Context): ThemePreferences {
        return ThemePreferences(context)
    }
}
