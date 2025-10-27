package com.shroomlife.shliste.modules.recipes

import com.shroomlife.shliste.modules.lists.ListItem
import kotlinx.serialization.*

@Serializable
data class RecipeStep(
    val uuid: String,
    val description: String,
    val order: Int
)

@Serializable
data class RecipeIngredient(
    val uuid: String,
    val name: String,
    val quantity: String
)

@Serializable
data class Recipe(
    val uuid: String,
    val name: String,
    val color: String,
    val ingredients: List<RecipeIngredient> = emptyList(),
    val steps: List<RecipeStep> = emptyList(),
    val lastEditied: Long = System.currentTimeMillis()
)
