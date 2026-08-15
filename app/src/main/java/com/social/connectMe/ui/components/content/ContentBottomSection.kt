package com.social.connectMe.ui.components.content

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.social.connectMe.R
import com.social.connectMe.ui.components.buttons.ButtonWithLabel

@Composable
fun ContentBottomSection(
    id: Int
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp, 5.dp, 5.dp, 1.dp)

    ) {
        Row() {
            ButtonWithLabel(
                "4",
                painterResource(R.drawable.like_outline_button),
                onClick = { value ->
                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
                },
                isRow = true,
                iconSize = 40.dp
            )
            ButtonWithLabel(
                "14", painterResource(R.drawable.comment_outline_button), onClick = { value ->

                }, isRow = true,
                iconSize = 40.dp
            )
            ButtonWithLabel(
                "150", painterResource(R.drawable.like_outline_button), onClick = { value ->

                }, isRow = true,
                iconSize = 40.dp
            )
        }

        ContentComment(
            id,
            "Pitabash1998",
            "ing Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in thei",
        )
    }
}
