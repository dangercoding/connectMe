package com.social.connectMe.ui.components

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

/**
 * A generic component to handle permission requests with an optional delay.
 */
@Composable
fun PermissionHandler(
    permissions: Array<String>,
    delayMillis: Long = 2000L,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit = {},
    checkGranted: (Context) -> Boolean = { context ->
        permissions.any {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
) {
    val context = LocalContext.current
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        // Check if any of the requested permissions were granted
        val isGranted = result.values.any { it }
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (checkGranted(context)) {
            onPermissionGranted()
        } else {
            if (delayMillis > 0) {
                delay(delayMillis)
            }
            launcher.launch(permissions)
        }
    }
}
