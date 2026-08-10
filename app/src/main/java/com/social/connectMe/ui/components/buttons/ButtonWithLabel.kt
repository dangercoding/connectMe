package com.social.connectMe.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithLabel(
    label: String,
    painter: Painter,
    onClick: (id:String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
         modifier = Modifier.clickable {
            onClick(label)
        }
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(40.dp)
        )
        Text(label)
    }
}

