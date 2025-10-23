package com.shroomlife.shliste.modules.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.R
import com.shroomlife.shliste.ui.theme.ZainFontFamily

@Composable
fun ListBottomBar() {

    val listStore = LocalListStore.current
    var name by remember { mutableStateOf("") }

    fun handleAddItem() {
        if(name.isNotBlank()) {
            listStore.addItemToCurrentList(name.trim())
            name = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column() {

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    placeholder = { Text("Neuer Eintrag", fontSize = 20.sp, fontFamily = ZainFontFamily) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = ZainFontFamily
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            handleAddItem()
                        }
                    )
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF2d9c74),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            handleAddItem()
                        }
                        .align(Alignment.CenterVertically)
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_plus),
                        contentDescription = "Hinzufügen",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }


            }
        }
    }
}