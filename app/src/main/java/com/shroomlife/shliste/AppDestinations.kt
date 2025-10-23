package com.shroomlife.shliste
enum class AppDestinations(
    val label: String,
    val icon: Int,
    val route: String
) {
    LISTS("Listen", R.drawable.icon_lists, "lists"),
    RECIPES("Rezepte", R.drawable.icon_recipes, "recipes"),
//    PRODUCTS("Produkte", R.drawable.icon_products, "products"),
//    STORES("Supermärkte", R.drawable.icon_stores, "stores"),
}
