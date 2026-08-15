package com.social.connectMe.core.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * State holder to manage hiding/showing UI components (like Toolbars and BottomBars) during scroll.
 */
class ScrollHidingState(
    val topBarMaxHeightPx: Float,
    val bottomBarMaxHeightPx: Float
) {
    // Offset for Top Bar: ranges from [-topBarMaxHeightPx, 0]
    var topBarOffset by mutableFloatStateOf(0f)
        private set

    // Offset for Bottom Bar: ranges from [0, bottomBarMaxHeightPx]
    var bottomBarOffset by mutableFloatStateOf(0f)
        private set

    val nestedScrollConnection = GenericScrollConnection(
        onScroll = { delta ->
            val oldTopOffset = topBarOffset
            val newTopOffset = (oldTopOffset + delta).coerceIn(-topBarMaxHeightPx, 0f)
            topBarOffset = newTopOffset

            val oldBottomOffset = bottomBarOffset
            val newBottomOffset = (oldBottomOffset - delta).coerceIn(0f, bottomBarMaxHeightPx)
            bottomBarOffset = newBottomOffset

            // Return 0f to indicate we're not consuming the scroll (allowing the list to scroll)
            0f
        }
    )
}

@Composable
fun rememberScrollHidingState(
    topBarHeight: Dp = 0.dp,
    bottomBarHeight: Dp = 0.dp,
    extraTopPadding: Dp = 0.dp,
    statusBarHeight: Dp = 0.dp
): ScrollHidingState {
    val density = LocalDensity.current
    val topPx = with(density) { (topBarHeight + extraTopPadding + statusBarHeight).toPx() }
    val bottomPx = with(density) { bottomBarHeight.toPx() }
    
    return remember(topPx, bottomPx) {
        ScrollHidingState(topPx, bottomPx)
    }
}
