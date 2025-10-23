package com.shroomlife.shliste

object Routes {
    const val LISTS = "lists"
    const val RECIPES = "recipes"
    const val PRODUCTS = "products"
    const val STORES = "stores"

    const val LIST_DETAIL = "lists/{listId}"
    fun listDetail(listId: String) = "lists/$listId"

    const val LIST_CREATE = "lists/create"
    fun listCreate() = "lists/create"

    const val LIST_EDIT = "lists/{listId}/edit"
    fun listEdit(listId: String) = "lists/$listId/edit"

    const val LIST_ITEM_EDIT = "lists/items/{listItemId}/edit"
    fun listItemEdit(listItemId: String) = "lists/items/$listItemId/edit"
}

fun navigateTo(
    navController: androidx.navigation.NavController,
    route: String
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    if (currentRoute != route) {
        navController.navigate(route)
    }
}
