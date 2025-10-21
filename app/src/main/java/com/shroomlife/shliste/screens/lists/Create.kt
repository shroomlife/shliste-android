package com.shroomlife.shliste.screens.lists

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsCreateScreen() {

    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        appStore.setBottomNav(BottomNavType.APP)
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