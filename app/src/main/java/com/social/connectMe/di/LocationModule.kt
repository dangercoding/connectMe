package com.social.connectMe.di

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.social.connectMe.data.repository.LocationRepositoryImpl
import com.social.connectMe.data.repository.NativeLocationRepositoryImpl
import com.social.connectMe.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    private const val USE_NATIVE_LOCATION = false // Flag to switch between Fused and Native

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        locationManager: LocationManager
    ): LocationRepository {
        return if (USE_NATIVE_LOCATION) {
            NativeLocationRepositoryImpl(context, locationManager)
        } else {
            LocationRepositoryImpl(context, fusedLocationClient)
        }
    }
}
