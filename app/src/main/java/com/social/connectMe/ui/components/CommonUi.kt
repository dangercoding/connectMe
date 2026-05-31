package com.social.connectMe.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SocialButton(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .border(1.dp, Color.Gray.copy(0.3f), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color.Gray.copy(0.4f),
    unfocusedBorderColor = Color.Gray.copy(0.2f),
    cursorColor = Color.White,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLeadingIconColor = Color.Gray,
    unfocusedLeadingIconColor = Color.Gray
)
