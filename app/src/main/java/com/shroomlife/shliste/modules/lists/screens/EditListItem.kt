package com.shroomlife.shliste.modules.lists.screens

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.AppContainer
import com.shroomlife.shliste.components.BackButton
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.PrimaryColor
import com.shroomlife.shliste.ui.theme.ZainFontFamily

@Composable
fun ListItemEditScreen(listItemId: String) {

    val listStore = LocalListStore.current
    val navController = LocalNavController.current
    val context = LocalContext.current

    val listItem = listStore.getListItemById(listItemId)
    val listId = listStore.getListIdByListItemId(listItemId)

    if(listItem == null || listId == null) {
        Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
        navigateTo(navController, Routes.LISTS)
        return
    }

    var itemName by remember { mutableStateOf<String>(listItem.name) }
    var itemQuantity by remember { mutableStateOf(listItem.quantity.toString()) }

    val focusManager = LocalFocusManager.current
    val quantityFocusRequester = remember { FocusRequester() }

    fun handleSaveListItem() {
        val quantityInt = itemQuantity.toIntOrNull() ?: 0
        if(itemName.isNotBlank() && quantityInt > 0) {
            listStore.updateListItemName(listItemId, itemName.trim())
            listStore.updateListItemQuantity(listItemId, itemQuantity.toInt())
            navigateTo(navController, Routes.listDetail(listId))
            Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show()
        }
    }

    AppContainer(
        bottomBar = {},
        disableScroll = true,
        beforePadding = {
            BackButton(
                to = Routes.listDetail(listId),
                caption = "Zurück zur Liste"
            )
        },
        isLoading = listStore.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),

                ) {
                Text("Bearbeite Eintrag", style = MaterialTheme.typography.displayLarge)

                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    singleLine = true,
                    label = { Text("Name des Eintrags", fontFamily = ZainFontFamily, fontSize = 14.sp) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            quantityFocusRequester.requestFocus()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ZainFontFamily
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(end = 8.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                )

                TextField(
                    value = itemQuantity,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            val numeric = newValue.toIntOrNull() ?: 0
                            if (numeric <= 999) { // 👈 Maximum-Grenze
                                itemQuantity = newValue
                            }
                        }
                    },
                    singleLine = true,
                    label = { Text("Anzahl", fontFamily = ZainFontFamily, fontSize = 14.sp) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            handleSaveListItem()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ZainFontFamily
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(end = 8.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .focusRequester(quantityFocusRequester)
                )

                Button(
                    onClick = {
                        handleSaveListItem()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Speichern",
                        fontFamily = ZainFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }

}