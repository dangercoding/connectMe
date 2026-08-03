package com.social.connectMe.ui.components.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun ContentComment(
    username: String,
    comment: String
) {
    var expanded by remember { mutableStateOf(false) }
    var isTruncated by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(start = 11.dp, end = 1.dp, top = 0.dp, bottom = 5.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (isTruncated || expanded) {
                    expanded = !expanded
                }
            },
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(username)
                }
                append(" ")
                append(comment)
                
                if (expanded) {
                    append(" ")
                    withStyle(SpanStyle(color = Color.Gray, fontWeight = FontWeight.Medium)) {
                        append("less")
                    }
                }
            },
            maxLines = if (expanded) Int.MAX_VALUE else 1,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                if (!expanded) {
                    isTruncated = textLayoutResult.hasVisualOverflow
                }
            },
            modifier = Modifier.weight(1f, fill = false)
        )

        if (isTruncated && !expanded) {
            Text(
                text = " more",
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
