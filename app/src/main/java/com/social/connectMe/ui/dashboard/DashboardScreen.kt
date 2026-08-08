package com.social.connectMe.ui.dashboard

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.core.components.GenericScrollConnection
import com.social.connectMe.ui.components.PermissionHandler
import com.social.connectMe.ui.components.content.ContentCard
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val density = LocalDensity.current

    // Separate flags to ensure both "Scrolled" and "Stopped" toasts are shown exactly once
    var hasShownScrolledToast by rememberSaveable { mutableStateOf(false) }
    var hasShownStoppedToast by rememberSaveable { mutableStateOf(false) }

    PermissionHandler(
        permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        delayMillis = 3000L,
        onPermissionGranted = {
            viewModel.observeLocationUpdates()
        },
        onPermissionDenied = {
            viewModel.onPermissionDenied()
        }
    )

    // Manual toolbar setup with status bar awareness and tighter breathing room
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val extraTopPadding = 8.dp // Balanced gap from notch area
    val toolbarHeight = 64.dp
    
    // Total height of the top assembly that needs to hide fully (Bar + Safe Area + Margin)
    val totalToolbarHeightPx = with(density) { (toolbarHeight + statusBarHeight + extraTopPadding).toPx() }
    var toolbarOffsetHeightPx by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember(totalToolbarHeightPx) {
        GenericScrollConnection(
            onScrollStarted = {
                if (!hasShownScrolledToast) {
                    Toast.makeText(context, "Scrolled", Toast.LENGTH_SHORT).show()
                    hasShownScrolledToast = true
                }
            },
            onScroll = { delta ->
                val oldOffset = toolbarOffsetHeightPx
                val newOffset = oldOffset + delta
                
                // Coerce offset within [-totalHeight, 0] to ensure the entire top area can hide
                toolbarOffsetHeightPx = newOffset.coerceIn(-totalToolbarHeightPx, 0f)

                // Trigger pagination logic if scrolling down significantly
                if (delta < -10f && !state.isLoading && !state.endReached) {
                    viewModel.loadNextItems()
                }

                // Return 0f so the list scrolls simultaneously with the toolbar hiding ("not first")
                0f
            },
            onScrollStopped = {
                if (!hasShownStoppedToast) {
                    Toast.makeText(context, "Stopped", Toast.LENGTH_SHORT).show()
                    hasShownStoppedToast = true
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Bottom) // Corrected: Use standard inset logic
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isPermissionDenied) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    PermissionDeniedContent(onOpenSettings = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    })
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    // Padding content so it starts below the fully assembled top bar
                    contentPadding = PaddingValues(
                        top = toolbarHeight + statusBarHeight + extraTopPadding,
                        bottom = 16.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    itemsIndexed(
                        items = state.items,
                        key = { _, id -> id }
                    ) { index, id ->
                        if (index >= state.items.size - 1 && !state.isLoading && !state.endReached) {
                            viewModel.loadNextItems()
                        }
                        ContentCard(id)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (state.isLoading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(32.dp),
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    if (state.endReached && state.items.isNotEmpty()) {
                        item {
                            Text(
                                text = "You've seen all posts",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Toolbar Assembly: Background + Status Bar Safe Area + Extra Margin + TopAppBar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.roundToInt()) }
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Background covers status bar area
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                // Minimal margin from top
                Spacer(Modifier.height(extraTopPadding))
                TopAppBar(
                    modifier = Modifier.height(toolbarHeight),
                    windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp), // Handled by container padding
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
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
                                Icons.Default.Add,
                                contentDescription = "Add Content",
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
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
                                    tint = Color.Black,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun PermissionDeniedContent(onOpenSettings: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
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
    latitude: Double?,
    longitude: Double?,
    error: String?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Your Location",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (latitude != null && longitude != null) {
            Text(
                text = "Lat: $latitude",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Lng: $longitude",
                style = MaterialTheme.typography.bodyLarge
            )
        } else if (error != null) {
            Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Text(
                text = "Fetching location...",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
