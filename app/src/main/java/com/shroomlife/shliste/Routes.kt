package com.shroomlife.shliste

import androidx.navigation.NavController

object Routes {

    // List Routes
    const val LISTS = "lists"
    const val LIST_CREATE = "lists/create"

    const val LIST_DETAIL = "lists/{listId}"
    fun listDetail(listId: String) = "lists/$listId"

    const val LIST_EDIT = "lists/{listId}/edit"
    fun listEdit(listId: String) = "lists/$listId/edit"

    const val LIST_ITEM_EDIT = "lists/items/{listItemId}/edit"
    fun listItemEdit(listItemId: String) = "lists/items/$listItemId/edit"

    // Recipe Routes
    const val RECIPES = "recipes"
    const val RECIPE_CREATE = "recipes/create"

    const val RECIPE_DETAIL = "recipes/{recipeId}"
    fun recipeDetail(recipeId: String) = "recipes/$recipeId"

    const val RECIPE_EDIT = "recipes/{recipeId}/edit"
    fun recipeEdit(recipeId: String) = "recipes/$recipeId/edit"

}

fun navigateTo(
    navController: NavController,
    route: String,
    popUpToRoute: String? = null
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    if (currentRoute != route) {
        navController.navigate(route) {
            popUpToRoute?.let {
                popUpTo(it) {
                    inclusive = true
                }
            }
        }
    }
}

