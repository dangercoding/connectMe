package com.social.connectMe.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.social.connectMe.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setMinUpdateIntervalMillis(2000)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.lastLocation?.let { location ->
                    launch { send(location) }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e)
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<Location> {
        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    continuation.resume(Result.success(location))
                } else {
                    // Fallback: request a single update if last location is null
                    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                        .setMaxUpdates(1)
                        .build()
                    
                    val callback = object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            val loc = result.lastLocation
                            if (loc != null) {
                                continuation.resume(Result.success(loc))
                            } else {
                                continuation.resume(Result.failure(Exception("Location is null")))
                            }
                            fusedLocationClient.removeLocationUpdates(this)
                        }
                    }
                    fusedLocationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
                }
            }.addOnFailureListener { e ->
                continuation.resume(Result.failure(e))
            }
        }
    }
}
