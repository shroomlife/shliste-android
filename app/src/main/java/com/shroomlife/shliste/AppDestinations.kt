package com.shroomlife.shliste
enum class AppDestinations(
    val label: String,
    val icon: Int,
    val route: String
) {
    LISTS("Listen", R.drawable.icon_lists, Routes.LISTS),
    RECIPES("Rezepte", R.drawable.icon_recipes, Routes.RECIPES),
//    PRODUCTS("Produkte", R.drawable.icon_products, "products"),
//    STORES("Supermärkte", R.drawable.icon_stores, "stores"),
}
