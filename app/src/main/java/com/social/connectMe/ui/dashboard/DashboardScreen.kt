package com.social.connectMe.ui.dashboard

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.core.components.GenericScrollConnection
import com.social.connectMe.ui.components.BottomNavIcon
import com.social.connectMe.ui.components.ConnectMeBottomAppBar
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

    // Navigation state
    var selectedTab by rememberSaveable { mutableStateOf("home") }

    // Bottom Navigation Items
    val navItems = remember {
        listOf(
            BottomNavItem("home", Icons.Default.Home, "Home"),
            BottomNavItem("favorite", Icons.Default.FavoriteBorder, "Liked"),
            BottomNavItem("mail", Icons.Default.MailOutline, "Messages"),
            BottomNavItem("menu", Icons.Default.Menu, "Menu"),
            BottomNavItem("profile", Icons.Default.Person, "Profile")
        )
    }

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

    // Manual toolbar setup with status bar awareness
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val extraTopPadding = 8.dp
    val toolbarHeight = 64.dp
    val totalToolbarHeightPx = with(density) { (toolbarHeight + statusBarHeight + extraTopPadding).toPx() }
    var toolbarOffsetHeightPx by remember { mutableFloatStateOf(0f) }

    // Bottom bar height - measured dynamically to ensure it can be fully hidden
    var bottomBarHeightPx by remember { mutableFloatStateOf(0f) }
    var bottomBarOffsetHeightPx by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember(totalToolbarHeightPx) {
        GenericScrollConnection(
            onScrollStarted = {
                if (!hasShownScrolledToast) {
                    Toast.makeText(context, "Scrolled", Toast.LENGTH_SHORT).show()
                    hasShownScrolledToast = true
                }
            },
            onScroll = { delta ->
                // Top Bar
                toolbarOffsetHeightPx = (toolbarOffsetHeightPx + delta).coerceIn(-totalToolbarHeightPx, 0f)

                // Bottom Bar (hides by moving DOWN, so offset increases when finger moves UP / delta < 0)
                if (bottomBarHeightPx > 0f) {
                    bottomBarOffsetHeightPx = (bottomBarOffsetHeightPx - delta).coerceIn(0f, bottomBarHeightPx)
                }

                // Trigger pagination logic if scrolling down significantly
                if (delta < -10f && !state.isLoading && !state.endReached) {
                    viewModel.loadNextItems()
                }

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
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // Full control including system bars
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(
                    top = toolbarHeight + statusBarHeight + extraTopPadding,
                    bottom = if (bottomBarHeightPx > 0) with(density) { bottomBarHeightPx.toDp() } else 80.dp + 16.dp
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

            // Bottom Navigation Bar - Placed in Box to allow full hiding below the screen edge
            ConnectMeBottomAppBar(
                items = navItems.map { 
                    com.social.connectMe.ui.components.BottomNavItem(
                        id = it.id,
                        icon = BottomNavIcon.Vector(it.icon as ImageVector),
                        label = it.label
                    )
                },
                selectedItemId = selectedTab,
                onItemClick = { id ->
                    selectedTab = id
                    Toast.makeText(context, "Clicked: $id", Toast.LENGTH_SHORT).show()
                },
                offsetY = { bottomBarOffsetHeightPx },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onSizeChanged { size ->
                        bottomBarHeightPx = size.height.toFloat()
                    }
            )

            // Toolbar Assembly
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.roundToInt()) }
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                Spacer(Modifier.height(extraTopPadding))
                TopAppBar(
                    modifier = Modifier.height(toolbarHeight),
                    windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
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
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

// Local helper for nav items
private data class BottomNavItem(
    val id: String,
    val icon: Any, // Can be ImageVector
    val label: String
)
