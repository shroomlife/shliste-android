package com.shroomlife.shliste.modules.lists.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.R

@Composable
fun DeleteListButton(
    onRemoved: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Bestätigung") },
            icon = {
                Icon(
                    painterResource(R.drawable.icon_trash),
                    contentDescription = "Löschen",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(32.dp)
                )
            },
            text = { Text("Bist du sicher, dass du die Liste löschen möchtest?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onRemoved()
                    }
                ) {
                    Text("Ja")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Nein")
                }
            }
        )
    }

    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        onClick = { showDialog = true },
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.icon_trash),
                contentDescription = "Löschen",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(14.dp)
            )
            Text(text = "Liste löschen", style = MaterialTheme.typography.bodySmall)
        }
    }
}
