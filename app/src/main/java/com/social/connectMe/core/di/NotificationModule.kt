package com.social.connectMe.core.di

import android.content.Context
import com.social.connectMe.core.services.NotificationHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationHandler(@ApplicationContext context: Context): NotificationHandler {
        return NotificationHandler(context)
    }
}
