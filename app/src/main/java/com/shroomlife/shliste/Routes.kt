package com.shroomlife.shliste

import androidx.navigation.NavController

object Routes {
    const val LISTS = "lists"
    const val LIST_DETAIL = "lists/{listId}"
    fun listDetail(listId: String) = "lists/$listId"
    const val RECIPES = "recipes"
    const val RECIPE_DETAIL = "recipes/{recipeId}"
    fun recipeDetail(recipeId: String) = "recipes/$recipeId"
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
                launchSingleTop = true
                popUpTo(it) {
                    inclusive = true
                }
            }
        }
    }
}

