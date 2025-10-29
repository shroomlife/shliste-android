package com.shroomlife.shliste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.DefaultLightGray

@Composable
fun BackButton(
    caption: String = "Zurück zur Übersicht",
    icon: Int = R.drawable.icon_arrow_left,
    iconAlt: String = "Zurück",
    to: String? = null
) {
    val navController = LocalNavController.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (to != null) {
                        val popped = navController.popBackStack(to, inclusive = false)
                        if (!popped) {
                            navigateTo(navController, to, to)
                        }
                    } else {
                        navController.popBackStack()
                    }
                }
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = iconAlt,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = caption
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline
        )
    }
}
