package com.social.connectMe.ui.components.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContentCard() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ContentProfileSection()

    }
}
