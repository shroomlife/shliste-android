package com.shroomlife.shliste.modules.recipes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.AppContainer
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.LocalRecipeStore
import com.shroomlife.shliste.R
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.components.LightBadge
import com.shroomlife.shliste.components.PrimaryFloatingButton
import com.shroomlife.shliste.modules.recipes.components.RecipeCard
import com.shroomlife.shliste.modules.recipes.components.RecipeCreateSheet
import com.shroomlife.shliste.navigateTo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesOverviewScreen() {

    val navController = LocalNavController.current
    val recipeStore = LocalRecipeStore.current

    val recipes = recipeStore.recipes.sortedByDescending {
        it.lastEditied
    }

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var showCreateSheet by remember { mutableStateOf(false) }
    val recipeCreateSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showCreateSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    recipeCreateSheetState.hide()
                    showCreateSheet = false
                }
            },
            sheetState = recipeCreateSheetState
        ) {
            RecipeCreateSheet(
                onSaved = { newRecipeId ->
                    scope.launch {
                        recipeCreateSheetState.hide()
                        showCreateSheet = false
                        navigateTo(navController, Routes.recipeDetail(newRecipeId))
                    }
                }
            )
        }
    }

    AppContainer(
        floatingActionButton = {
            PrimaryFloatingButton(
                caption = "Neues Rezept",
                onClick = {
                    scope.launch {
                        showCreateSheet = true
                        recipeCreateSheetState.show()
                    }
                }
            )
        },
        isLoading = recipeStore.isLoading,
        disableScroll = true,
        disablePadding = true
    ) {

        if(recipes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Keine Rezepte vorhanden.")
            }
        } else {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(recipes, key = { it.uuid }) { recipe ->
                    RecipeCard(
                        recipe = recipe,
                        onClick = { uuid ->
                            navigateTo(navController, Routes.recipeDetail(uuid))
                        }
                    ) {
                        LightBadge(
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.icon_products),
                                    contentDescription = null,
                                    tint = Color(0xFF111827),
                                    modifier = Modifier
                                        .size(14.dp)
                                )
                            }
                        ) {
                            Text(
                                text = if(recipe.ingredients.size == 1) {
                                    "1 Zutat"
                                } else {
                                    "${recipe.ingredients.size} Zutaten"
                                },
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

        }

    }
}
