package com.social.connectMe.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.social.connectMe.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class NativeLocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationManager: LocationManager
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(): Flow<Location> = callbackFlow {
        val listener = LocationListener { location ->
            trySend(location)
        }

        val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LocationManager.GPS_PROVIDER
        } else {
            LocationManager.NETWORK_PROVIDER
        }

        locationManager.requestLocationUpdates(
            provider,
            5000L,
            0f,
            listener
        )

        awaitClose {
            locationManager.removeUpdates(listener)
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<Location> {
        return suspendCancellableCoroutine { continuation ->
            val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                LocationManager.GPS_PROVIDER
            } else {
                LocationManager.NETWORK_PROVIDER
            }

            val location = locationManager.getLastKnownLocation(provider)
            if (location != null) {
                continuation.resume(Result.success(location))
            } else {
                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        locationManager.removeUpdates(this)
                        continuation.resume(Result.success(location))
                    }
                }
                locationManager.requestLocationUpdates(provider, 0L, 0f, listener)
            }
        }
    }
}
