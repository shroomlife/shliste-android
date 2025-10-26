package com.shroomlife.shliste.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCard(
    header: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    backgroundColor: Color = Color.White,
    onClick: (() -> Unit)? = null,
    onClickHeader: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = if(onClick != null) {
            Modifier
                .fillMaxWidth()
                .clickable(enabled = true) {
                    onClick()
                }
        } else {
            Modifier
                .fillMaxWidth()
        },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column() {
            if (header != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if(onClickHeader != null) {
                                Modifier.clickable(enabled = true) {
                                    onClickHeader()
                                }
                            } else {
                                Modifier
                            }
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    backgroundColor,
                                    Color.White
                                )
                            )
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
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