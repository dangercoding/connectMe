package com.social.connectMe.ui.components.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import com.social.connectMe.ui.components.buttons.ButtonWithLabel

@Composable
fun ContentBottomSection(){

    Column(){
       // ContentLikeSection()
//        ContentCommentSection()
//        ContentShareSection()
        Row() {
            ButtonWithLabel("4", Icons.Default.FavoriteBorder)
            ButtonWithLabel("14", Icons.Default.Notifications)
        }
    }
}