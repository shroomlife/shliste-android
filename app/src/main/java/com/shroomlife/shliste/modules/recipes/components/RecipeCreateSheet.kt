package com.shroomlife.shliste.modules.recipes.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.LocalRecipeStore
import com.shroomlife.shliste.R
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.ZainFontFamily

@Composable
fun RecipeCreateSheet(
    onSaved: ((String) -> Unit)? = null
) {

    val appStore = LocalAppStore.current
    val recipeStore = LocalRecipeStore.current
    val navController = LocalNavController.current
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }

    fun handleAddRecipe() {
        if(name.isNotBlank()) {
            val newRecipeId: String = recipeStore.addRecipe(name.trim())
            navigateTo(navController, Routes.recipeDetail(newRecipeId))
            Toast.makeText(context, "✅ Rezept Erstellt", Toast.LENGTH_SHORT).show()
            onSaved?.invoke(newRecipeId)
        }
    }

    LaunchedEffect(Unit) {
        name = ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Neues Rezept", style = MaterialTheme.typography.displayLarge)
            }

            TextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                label = { Text("Name des Rezepts", fontFamily = ZainFontFamily, fontSize = 14.sp) },
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
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        handleAddRecipe()
                    }
                )
            )

            Button(
                onClick = {
                    handleAddRecipe()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
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

//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Bottom
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//
//            ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Bearbeite Liste", style = MaterialTheme.typography.displayLarge)
//                if(appStore.biometricAvailable == true) {
//                    Switch(
//                        checked = isSecret,
//                        onCheckedChange = {
//                            isSecret = it
//                        },
//                        thumbContent = if (isSecret) {
//                            {
//                                Icon(
//                                    painterResource(R.drawable.icon_lock_private),
//                                    contentDescription = null,
//                                    modifier = Modifier.size(SwitchDefaults.IconSize),
//                                )
//                            }
//                        } else {
//                            {
//                                Icon(
//                                    painterResource(R.drawable.icon_lock_public),
//                                    contentDescription = null,
//                                    modifier = Modifier.size(SwitchDefaults.IconSize),
//                                )
//                            }
//                        }
//                    )
//                }
//            }
//
//            TextField(
//                value = name,
//                onValueChange = { name = it },
//                singleLine = true,
//                label = { Text("Name der Liste", fontFamily = ZainFontFamily, fontSize = 14.sp) },
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.White,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedContainerColor = Color.White,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                textStyle = TextStyle(
//                    fontSize = 24.sp,
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontFamily = ZainFontFamily
//                ),
//                shape = RoundedCornerShape(8.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(64.dp)
//                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        handleSaveList()
//                    }
//                )
//            )
//
//            Button(
//                onClick = {
//                    handleSaveList()
//                },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = PrimaryColor,
//                    contentColor = Color.White
//                )
//            ) {
//                Text(
//                    text = "Speichern",
//                    fontFamily = ZainFontFamily,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//    }

}