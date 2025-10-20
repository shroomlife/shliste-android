package com.shroomlife.shliste

object Routes {
    const val LISTS = "lists"
    const val RECIPES = "recipes"
    const val PRODUCTS = "products"
    const val STORES = "stores"

    const val LIST_DETAIL = "lists/{listId}"
    fun listDetail(listId: String) = "lists/$listId"
}
