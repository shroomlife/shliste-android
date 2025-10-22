package com.shroomlife.shliste.modules.lists.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
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

    var name by remember { mutableStateOf("") }

    fun handleAddList() {
        if(name.isNotBlank()) {
            listStore.addList(name.trim())
            navController.navigate(Routes.LISTS)
            Toast.makeText(context, "✅ Liste Erstellt", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        name = ""
    }

    AppContainer {
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
                singleLine = true,
                label = { Text("Name der Liste") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        handleAddList()
                    }
                )
            )

            Button(
                onClick = {
                    handleAddList()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Speichern")
            }
        }
    }
}