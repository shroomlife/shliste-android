package com.shroomlife.shliste.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.PrimaryColor

@Composable
fun PrimaryFloatingButton(
    to: String,
    caption: String,
    icon: Int = R.drawable.icon_plus
) {

    val navController = LocalNavController.current

    ExtendedFloatingActionButton(
        onClick = {
            navigateTo(navController, to)
        },
        containerColor = PrimaryColor,
        icon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.width(24.dp)
            )
        },
        text = { Text(text = caption, color = Color.White, style = MaterialTheme.typography.bodyLarge) },
    )

}