package com.shroomlife.shliste.modules.lists.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.components.DefaultCard
import com.shroomlife.shliste.utils.ColorUtils


@Composable
fun ListCard(
    uuid: String,
    name: String,
    color: String,
    onClick: ((String) -> Unit)? = null,
    onClickHeader: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    DefaultCard(
        backgroundColor = ColorUtils.colorWith20Opacity(color),
        header = {
            Text(
                text = name,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        },
        onClick = if (onClick != null) {
            {
                onClick(uuid)
            }
        } else {
            null
        },
        onClickHeader = onClickHeader
    ) {
        content()
    }
}
