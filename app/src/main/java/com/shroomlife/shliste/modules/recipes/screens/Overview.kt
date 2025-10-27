package com.shroomlife.shliste.modules.recipes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.shroomlife.shliste.navigateTo

@Composable
fun RecipesOverviewScreen() {

    val navController = LocalNavController.current
    val recipeStore = LocalRecipeStore.current

    val recipes = recipeStore.recipes.sortedByDescending {
        it.lastEditied
    }

    AppContainer(
        floatingActionButton = {
            PrimaryFloatingButton(
                to = Routes.RECIPE_CREATE,
                caption = "Neues Rezept",
            )
        }
    ) {

        if(recipes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Keine Rezepte vorhanden.")
            }
        } else {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (recipe in recipes) {
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
