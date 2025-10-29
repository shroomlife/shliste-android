package com.shroomlife.shliste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LightBadge(
    icon: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 6.dp,
                bottom = 6.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.invoke()
            content()
        }
    }
}