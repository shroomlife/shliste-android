package com.shroomlife.shliste.modules.lists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.ui.theme.PrimaryColor

@Composable
fun ListItemEditSheetContent(
    item: com.shroomlife.shliste.modules.lists.ListItem,
    onSave: (String, Int) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Eintrag bearbeiten", style = MaterialTheme.typography.headlineSmall)

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true
        )

        TextField(
            value = quantity,
            onValueChange = { if (it.all { c -> c.isDigit() }) quantity = it },
            label = { Text("Menge") },
            singleLine = true
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) { Text("Abbrechen") }

            Button(
                onClick = {
                    val qty = quantity.toIntOrNull() ?: 1
                    onSave(name.trim(), qty)
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) { Text("Speichern") }
        }
    }
}
