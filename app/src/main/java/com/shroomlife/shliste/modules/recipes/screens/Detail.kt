package com.shroomlife.shliste.modules.recipes.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.AppContainer
import com.shroomlife.shliste.LocalRecipeStore
import com.shroomlife.shliste.components.BackButton
import com.shroomlife.shliste.components.BiometricLoadingScreen
import com.shroomlife.shliste.modules.lists.components.DeleteListButton
import com.shroomlife.shliste.modules.lists.components.ListBottomBar
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.modules.lists.components.ListItem
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.utils.AppUtils

@Composable
fun RecipesDetailScreen(recipeId: String) {

    val context = LocalContext.current
    val recipeStore = LocalRecipeStore.current
    val navController = LocalNavController.current

    val isDeleted = remember { mutableStateOf<Boolean>(false) }

    recipeStore.setcurrentRecipeId(recipeId)
    val recipe = recipeStore.getCurrentRecipe()
    if(recipe == null) {
        if(isDeleted.value == true) return
        Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
        navigateTo(navController, Routes.RECIPES, Routes.recipeDetail(recipeId))
        return
    }

    AppContainer() {
        Text("Hallo ${recipe.name}!")
    }

//
//    val itemCount = list.items.size
//
//    var previousCount by remember { mutableStateOf(itemCount) }
//    var initialized by remember { mutableStateOf(false) }
//
//    LaunchedEffect(itemCount) {
//        if (!initialized) {
//            initialized = true
//            previousCount = itemCount
//            return@LaunchedEffect
//        }
//        if (itemCount > previousCount) {
//            val state = appStore.scrollState ?: return@LaunchedEffect
//            state.animateScrollTo(state.maxValue)
//        }
//        previousCount = itemCount
//    }
//
//    AppContainer(
//        beforePadding = {
//            BackButton(
//                to = Routes.LISTS
//            )
//        },
//        bottomBar = {
//            ListBottomBar()
//        }
//    ) {
//        Column(
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            ListCard(
//                uuid = list.uuid,
//                name = list.name,
//                color = list.color,
//                secret = list.secret,
//                onClickHeader = {
//                    navigateTo(navController, Routes.listEdit(recipeId))
//                }
//            ) {
//                if(list.items.isEmpty()) {
//                    Text(
//                        text = "Keine Einträge",
//                        color = Color(0xFF6b7280)
//                    )
//                } else {
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        for(item in list.items) {
//                            ListItem(item = item)
//                        }
//                    }
//                }
//            }
//            DeleteListButton(
//                onRemoved = {
//                    isDeleted.value = true
//                    navigateTo(navController, Routes.LISTS, Routes.listDetail(recipeId))
//                    listStore.removeList(recipeId)
//                }
//            )
//        }
//    }
}