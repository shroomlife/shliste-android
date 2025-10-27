package com.shroomlife.shliste.modules.recipes

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.modules.recipes.screens.RecipesCreateScreen
import com.shroomlife.shliste.modules.recipes.screens.RecipesDetailScreen
import com.shroomlife.shliste.modules.recipes.screens.RecipesOverviewScreen
import com.shroomlife.shliste.navigateTo

fun NavGraphBuilder.recipeNavGraph(navController: NavController) {
    composable(Routes.RECIPES) { RecipesOverviewScreen() }
    composable(Routes.RECIPE_CREATE) { RecipesCreateScreen() }
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
//    composable(
//        route = Routes.LIST_EDIT,
//        arguments = listOf(navArgument("listId") { type = NavType.StringType })
//    ) { backStackEntry ->
//        val listId = backStackEntry.arguments?.getString("listId")
//        if (listId === null) {
//            navigateTo(navController, Routes.LISTS)
//            return@composable
//        }
//        SecretListWrapper(listId = listId) {
//            ListEditScreen(listId = listId)
//        }
//    }
//    composable(
//        route = Routes.LIST_ITEM_EDIT,
//        arguments = listOf(navArgument("listItemId") { type = NavType.StringType })
//    ) { backStackEntry ->
//        val listItemId = backStackEntry.arguments?.getString("listItemId")
//        if (listItemId === null) {
//            navigateTo(navController, Routes.LISTS)
//            return@composable
//        }
//        val listId = LocalListStore.current.getListIdByListItemId(listItemId)
//        if (listId === null) {
//            navigateTo(navController, Routes.LISTS)
//            return@composable
//        }
//        SecretListWrapper(listId) {
//            ListItemEditScreen(listItemId)
//        }
//    }
}