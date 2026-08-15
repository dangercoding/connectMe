package com.social.connectMe.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonWithLabel(
    label: String?=null,
    painter: Painter,
    onClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    isRow: Boolean = true,
    iconSize: androidx.compose.ui.unit.Dp = 24.dp,
    tint: Color = Color.Black
) {
    if (isRow) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clickable {
                    onClick(label?:"")
                }
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(iconSize)
            )
            if (label!=null)
            Text(
                text = label,
                modifier = Modifier.padding(start = 0.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .clickable {
                    onClick(label?:"")
                }
                .padding(4.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(iconSize)
            )
            if (label!=null)
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp
            )
        }
    }
}
