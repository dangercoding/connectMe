package com.social.connectMe.domain.repository

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationUpdates(): Flow<Location>
    suspend fun getCurrentLocation(): Result<Location>
}
