package com.shroomlife.shliste.modules.recipes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.modules.recipes.screens.RecipesDetailScreen
import com.shroomlife.shliste.modules.recipes.screens.RecipesOverviewScreen
import com.shroomlife.shliste.navigateTo

fun NavGraphBuilder.RecipeRouter(navController: NavController) {
    composable(Routes.RECIPES) { RecipesOverviewScreen() }
    composable(
        route = Routes.RECIPE_DETAIL,
        arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
    ) { backStackEntry ->
        val recipeId = backStackEntry.arguments?.getString("recipeId")
        if (recipeId === null) {
            navigateTo(navController, Routes.RECIPES)
            return@composable
        }
        RecipesDetailScreen(recipeId)
    }
}