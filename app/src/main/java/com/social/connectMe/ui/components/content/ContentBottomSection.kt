package com.social.connectMe.ui.components.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.social.connectMe.ui.components.buttons.ButtonWithLabel

@Composable
fun ContentBottomSection(){

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(1.dp,5.dp,5.dp,1.dp)

    ){
       // ContentLikeSection()
//        ContentCommentSection()
//        ContentShareSection()
        Row() {
            ButtonWithLabel("4", Icons.Default.FavoriteBorder)
            ButtonWithLabel("14", Icons.Default.Notifications)
        }
    }
}