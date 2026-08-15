package com.social.connectMe.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.IntOffset
import com.social.connectMe.ui.components.buttons.ButtonWithLabel
import kotlin.math.roundToInt

sealed class BottomNavIcon {
    data class Vector(val imageVector: ImageVector) : BottomNavIcon()
    data class Drawable(val painter: Painter) : BottomNavIcon()
}

data class BottomNavItem(
    val id: String,
    val icon: BottomNavIcon,
    val label: String? = null
)

@Composable
fun ConnectMeBottomAppBar(
    items: List<BottomNavItem>,
    selectedItemId: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    offsetY: () -> Float = { 0f }
) {
    BottomAppBar(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(x = 0, y = offsetY().roundToInt()) },
        containerColor = containerColor,
        contentColor = contentColor,
        actions = {
            items.forEach { item ->
                val isSelected = item.id == selectedItemId
                val painter = when (val icon = item.icon) {
                    is BottomNavIcon.Vector -> rememberVectorPainter(icon.imageVector)
                    is BottomNavIcon.Drawable -> icon.painter
                }
                
                ButtonWithLabel(
                    label = item.label,
                    painter = painter,
                    onClick = { onItemClick(item.id) },
                    isRow = false,
                    modifier = Modifier.weight(1f),
                    tint = if (isSelected) selectedColor else contentColor.copy(alpha = 0.6f)
                )
            }
        }
    )
}
