package com.social.connectMe.ui.components.buttons


import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ButtonWithLabel(
    label: String,
    logo: ImageVector
) {

    Row() {
        Icon(logo, contentDescription = "Like")
        Text(label)
    }

}