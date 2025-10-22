package com.shroomlife.shliste.modules.lists.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.components.DefaultCard

@Composable
fun ListCard(
    uuid: String,
    name: String,
    color: Color,
    onClick: ((String) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    DefaultCard(
        backgroundColor = color,
        header = {
            Text(
                text = name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        onClick = if (onClick != null) {
            {
                onClick(uuid)
            }
        } else {
            null
        }
    ) {
        content()
    }
}
