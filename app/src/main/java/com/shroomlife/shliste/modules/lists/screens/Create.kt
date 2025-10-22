package com.shroomlife.shliste.modules.lists.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.components.AppContainer

@Composable
fun ListsCreateScreen() {

    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    val context = LocalContext.current

    LaunchedEffect(Unit) {
    }

    AppContainer {

        var name by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Neue Liste", style = MaterialTheme.typography.headlineLarge)

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name der Liste") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    listStore.addList(name)
                    listStore.saveListsToStorage(context)
                    navController.navigate(Routes.LISTS)
                    Toast.makeText(context, "✅ Liste Erstellt", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Speichern")
            }
        }
    }
}