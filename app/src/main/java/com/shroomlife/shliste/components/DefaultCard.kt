package com.shroomlife.shliste.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCard(
    content: @Composable () -> Unit,
    header: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    backgroundColor: Color = Color.White,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = {
            if (onClick != null) {
                onClick()
            }
        }
    ) {
        Column() {
            if (header != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    backgroundColor,
                                    Color.White
                                )
                            )
                        )
                        .padding(16.dp)
                ) {
                    header()
                }
                HorizontalDivider()
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                content()
            }

            if (footer != null) {
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    footer()
                }
            }
        }
    }
}