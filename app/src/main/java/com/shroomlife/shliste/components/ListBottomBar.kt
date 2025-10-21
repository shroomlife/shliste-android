package com.shroomlife.shliste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 24.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { /* links */ }) {
                Text("Left")
            }

            // Spacer, der den mittleren Container wachsen lässt
            Box(
                modifier = Modifier
                    .weight(1f) // <--- das macht den "grow" Effekt
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Center Content")
            }

            Button(onClick = { /* rechts */ }) {
                Text("Right")
            }
        }
    }
}