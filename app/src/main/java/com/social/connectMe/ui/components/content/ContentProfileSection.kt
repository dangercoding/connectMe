package com.social.connectMe.ui.components.content

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ContentProfileSection() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color.Red)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,


        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier

        ) {
//            Box(
//                modifier = Modifier
//                    .size(35.dp)
//                    .clip(CircleShape)
//            ) {
//                AsyncImage(
//                    model = "https://picsum.photos/200/300",
//                    contentDescription = "Profile image",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )
//            }
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://picsum.photos/200/300").crossfade(true).build(),
                    placeholder = painterResource(R.drawable.star_off),
                    contentDescription = "Profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape),
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(35.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Pitabash1998", style = TextStyle(
                        color = Color.Black,
                        fontSize = 13.sp,
                    )
                )
                Text(
                    text = "Blore, India", style = TextStyle(
                        color = Color.Black,
                        fontSize = 11.sp,
                    )
                )
            }
        }

        Icon(
            Icons.Default.Menu, contentDescription = "notification", tint = Color(
                0xFF000000
            ), modifier = Modifier.size(25.dp)
        )

    }

}