package com.social.connectMe.ui.dashboard

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.ui.components.PermissionHandler
import com.social.connectMe.ui.components.content.ContentCard

class GenericScrollConnection(
    private val onScrollUp: () -> Unit = {},
    private val onScrollDown: () -> Unit = {},
    private val onScrollStarted: () -> Unit = {},
    private val onScrollStopped: () -> Unit = {}
) : NestedScrollConnection {

    private var isScrolling = false

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {

        if (!isScrolling) {
            isScrolling = true
            onScrollStarted()
        }

        when {
            available.y < 0 -> onScrollUp()
            available.y > 0 -> onScrollDown()
        }

        return Offset.Zero
    }

    override suspend fun onPostFling(
        consumed: Velocity,
        available: Velocity
    ): Velocity {

        isScrolling = false
        onScrollStopped()

        return Velocity.Zero
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToSettings: () -> Unit, viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // Flag to ensure the toast message is shown only once
    var hasShownScrollToast by rememberSaveable { mutableStateOf(false) }

    PermissionHandler(
        permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        ), delayMillis = 3000L, onPermissionGranted = {
            viewModel.observeLocationUpdates()
        }, onPermissionDenied = {
            viewModel.onPermissionDenied()
        })
    
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )

    val nestedScrollConnection = remember {
        GenericScrollConnection(
            onScrollStarted = {
                if (!hasShownScrollToast) {
                    Toast.makeText(context, "Scrolled", Toast.LENGTH_SHORT).show()
                    hasShownScrollToast = true
                }
            },
            onScrollUp = {
                // available.y < 0 means user is scrolling down (content moves up)
                viewModel.loadNextItems()
            }
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
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
                            Icons.Default.Add, contentDescription = "Add Content", tint = Color.Black,
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
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Using itemsIndexed for cleaner pagination trigger
            itemsIndexed(
                items = state.items,
                key = { _, id -> id } // Providing a key improves performance
            ) { index, id ->
                
                // Trigger pagination: load more when the user is near the end
                // We check if the current index is the last one in the current list
                if (index >= state.items.size - 1 && !state.isLoading && !state.endReached) {
                    viewModel.loadNextItems()
                }
                
                ContentCard(id)
                
                // Spacing between cards
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Show loading spinner at the bottom while fetching next page
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
            
            // Optional: Show a message when all items are loaded
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
