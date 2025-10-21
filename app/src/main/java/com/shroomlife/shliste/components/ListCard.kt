package com.shroomlife.shliste.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ListCard(
    uuid: String,
    title: String,
    content: String,
    color: Color,
    onClick: (String) -> Unit
) {
    DefaultCard(
        backgroundColor = color,
        header = {
            Text(
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        content = {
            Text(
                text = content,
                color = Color.DarkGray
            )
        },
        onClick = {
            onClick(uuid)
        },
    )
}
