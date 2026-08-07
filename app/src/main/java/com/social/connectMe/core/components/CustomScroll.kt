package com.social.connectMe.core.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

/**
 * Custom NestedScrollConnection to handle scroll events and trigger callbacks.
 * Returns the consumed vertical scroll amount.
 */
class GenericScrollConnection(
    private val onScroll: (delta: Float) -> Float, // delta -> consumed
    private val onScrollStarted: () -> Unit = {},
    private val onScrollStopped: () -> Unit = {}
) : NestedScrollConnection {

    private var isScrolling = false

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        if (!isScrolling && Math.abs(available.y) > 0.5f) {
            isScrolling = true
            onScrollStarted()
        }

        val consumed = onScroll(available.y)
        return Offset(0f, consumed)
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