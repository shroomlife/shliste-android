package com.shroomlife.shliste.modules.lists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.R
import com.shroomlife.shliste.components.DefaultCard
import com.shroomlife.shliste.modules.lists.Shliste
import com.shroomlife.shliste.utils.ColorUtils


@Composable
fun ListCard(
    list: Shliste,
    onClick: ((String) -> Unit)? = null,
    onClickHeader: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    DefaultCard(
        backgroundColor = ColorUtils.colorWith20Opacity(list.color),
        header = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = list.name,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                if(list.secret) {
                    Icon(
                        painterResource(R.drawable.icon_lock_private),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
        },
        onClick = if (onClick != null) {
            {
                onClick(list.uuid)
            }
        } else {
            null
        },
        onClickHeader = onClickHeader
    ) {
        content()
    }
}
