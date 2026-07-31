package com.social.connectMe.ui.components.buttons


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithLabel(
    label: String,
    logo: ImageVector
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(logo, contentDescription = "Like",
            tint = androidx.compose.ui.graphics.Color.Black,
            modifier = Modifier.size(30.dp)
            )
        Text(label)
    }

}