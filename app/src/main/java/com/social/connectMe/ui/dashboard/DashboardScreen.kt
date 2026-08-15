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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.social.connectMe.core.components.GenericScrollConnection
import com.social.connectMe.ui.components.PermissionHandler
import com.social.connectMe.ui.components.buttons.ButtonWithLabel
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
    val totalToolbarHeightPx =
        with(density) { (toolbarHeight + statusBarHeight + extraTopPadding).toPx() }
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
        bottomBar = {
            BottomAppBar(
                actions = {
                    ButtonWithLabel(
                        null,
                        painter = rememberVectorPainter(Icons.Default.Home),
                        onClick = { value ->
                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                        },
                        isRow = false,
                        modifier = Modifier.weight(1f)
                    )
                    ButtonWithLabel(
                        null,
                        rememberVectorPainter(Icons.Default.FavoriteBorder),
                        onClick = { value ->
                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                        },
                        isRow = false,
                        modifier = Modifier.weight(1f)
                    )
                    ButtonWithLabel(
                        null,
                        rememberVectorPainter(Icons.Default.MailOutline),
                        onClick = { value ->
                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                        },
                        isRow = false,
                        modifier = Modifier.weight(1f)
                    )
                    ButtonWithLabel(
                        null,
                        rememberVectorPainter(Icons.Default.Menu),
                        onClick = { value ->
                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                        },
                        isRow = false,
                        modifier = Modifier.weight(1f)
                    )
                    ButtonWithLabel(
                        null,
                        rememberVectorPainter(Icons.Default.Person),
                        onClick = { value ->
                            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
                        },
                        isRow = false,
                        modifier = Modifier.weight(1f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Red)
                    .padding(bottom = 10.dp)
                ,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,


            )
        },
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
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
