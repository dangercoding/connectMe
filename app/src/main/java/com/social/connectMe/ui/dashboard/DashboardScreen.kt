package com.social.connectMe.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.ui.components.PermissionHandler
import com.social.connectMe.ui.components.content.ContentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToSettings: () -> Unit, viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    PermissionHandler(
        permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    ), delayMillis = 3000L, onPermissionGranted = {
        viewModel.observeLocationUpdates()
    }, onPermissionDenied = {
        viewModel.onPermissionDenied()
    })

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, topBar = {

            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,

                        ) {
                        Icon(
                            Icons.Default.Add, contentDescription = "Add Content", tint = Color(
                                0xFF000000
                            ), modifier = Modifier.size(30.dp)
                        )

                        Text(
                            "Instagram",
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.titleLarge
                        )

                        IconButton(onClick = onNavigateToSettings) {
                            Icon(
                                Icons.Default.FavoriteBorder,
                                contentDescription = "notification",
                                tint = Color(
                                    0xFF000000
                                ),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                },
            )
        }) { paddingValues ->

// start of comment
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    MaterialTheme.colorScheme.background
//                )
//                .padding(paddingValues)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = state.welcomeMessage, style = MaterialTheme.typography.headlineMedium
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//
//            if (state.isPermissionDenied) {
//                PermissionDeniedContent(onOpenSettings = {
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                        data = Uri.fromParts("package", context.packageName, null)
//                    }
//                    context.startActivity(intent)
//                })
//            } else {
//                LocationInfo(
//                    latitude = state.latitude,
//                    longitude = state.longitude,
//                    error = state.locationError
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(onClick = { viewModel.refreshLocation() }) {
//                    Text("Refresh Location")
//                }
//            }
//        }


        // end of it


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                )
               // .padding(paddingValues)
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            repeat(50){
                item {
                    ContentCard()
                    Spacer(modifier = Modifier.height(24.dp))
                }

            }


        }
    }
}

@Composable
fun PermissionDeniedContent(onOpenSettings: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Location Permission Denied",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We need location permission to show your current position. Please enable it in settings.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onOpenSettings) {
            Text("Open Settings")
        }
    }
}

@Composable
fun LocationInfo(
    latitude: Double?, longitude: Double?, error: String?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Your Location", style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (latitude != null && longitude != null) {
            Text(
                text = "Lat: $latitude", style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Lng: $longitude", style = MaterialTheme.typography.bodyLarge
            )
        } else if (error != null) {
            Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Text(
                text = "Fetching location...", style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
