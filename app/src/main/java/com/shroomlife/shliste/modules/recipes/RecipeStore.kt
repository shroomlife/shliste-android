package com.shroomlife.shliste.modules.recipes

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shroomlife.shliste.utils.ColorUtils
import com.shroomlife.shliste.utils.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.collections.plus

class RecipeStore(application: Application) : AndroidViewModel(application) {

    private val _isLoading = mutableStateOf(true)
    private val _recipes = mutableStateListOf<Recipe>()
    private val _currentRecipeId = mutableStateListOf<String?>()
    private val storeFileName = "recipes.json"

    val recipes: List<Recipe> get() = _recipes
    val isLoading: Boolean get() = _isLoading.value

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadRecipes()
        }
    }

    fun addRecipe(name: String): String {
        val newRecipeId = UUID.randomUUID().toString()
        _recipes.add(
            Recipe(
                uuid = newRecipeId,
                name = name,
                color = ColorUtils.getRandomColor(),
            )
        )
        saveRecipesToStorage()
        return newRecipeId
    }

    fun removeRecipe(id: String) {
        _recipes.removeAll { it.uuid == id }
        saveRecipesToStorage()
    }

    fun getRecipeById(id: String): Recipe? {
        return _recipes.find { it.uuid == id }
    }

    fun getIngredientById(itemId: String): RecipeIngredient? {
        _recipes.forEach { recipe ->
            recipe.ingredients.forEach { item ->
                if (item.uuid == itemId) {
                    return item
                }
            }
        }
        return null
    }

    fun getRecipeIdByIngredientId(itemId: String): String? {
        _recipes.forEach { recipe ->
            recipe.ingredients.forEach { item ->
                if (item.uuid == itemId) {
                    return recipe.uuid
                }
            }
        }
        return null
    }

    fun getCurrentRecipe(): Recipe? {
        val currentRecipeId = _currentRecipeId.firstOrNull() ?: return null
        return getRecipeById(currentRecipeId)
    }

    fun removeRecipeIngredient(itemUuid: String) {
        val currentRecipeId = _currentRecipeId.firstOrNull() ?: return
        removeIngredientFromRecipe(currentRecipeId, itemUuid)
        saveRecipesToStorage()
    }

    fun addIngredientToCurrentRecipe(name: String, quantity: Int = 1) {
        val currentRecipeId = _currentRecipeId.firstOrNull() ?: return
        addIngredientToRecipe(currentRecipeId, name, quantity)
    }

    fun updateListName(listId: String, newName: String) {
        val listIndex = _recipes.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _recipes[listIndex]
            _recipes[listIndex] = current.copy(
                name = newName,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveRecipesToStorage()
    }

    fun updateIngredientName(ingredientId: String, newName: String): String {
        val recipeIndex = _recipes.indexOfFirst { recipe ->
            recipe.ingredients.any { it.uuid == ingredientId }
        }
        if (recipeIndex != -1) {
            val currentRecipe = _recipes[recipeIndex]
            val updatedIngredients = currentRecipe.ingredients.map { ingredient ->
                if (ingredient.uuid == ingredientId) {
                    ingredient.copy(
                        name = newName
                    )
                } else ingredient
            }

            _recipes[recipeIndex] = currentRecipe.copy(
                ingredients = updatedIngredients,
                lastEditied = System.currentTimeMillis()
            )
            saveRecipesToStorage()
            return currentRecipe.uuid
        }
        return ""
    }

    fun updateIngredientQuantity(ingredientId: String, newQuantity: Int): String {
        val recipeIndex = _recipes.indexOfFirst { recipe ->
            recipe.ingredients.any { it.uuid == ingredientId }
        }
        if (recipeIndex != -1) {
            val currentRecipe = _recipes[recipeIndex]
            val updatedIngredients = currentRecipe.ingredients.map { ingredient ->
                if (ingredient.uuid == ingredientId) {
                    ingredient.copy(
                        quantity = newQuantity.toString()
                    )
                } else ingredient
            }

            _recipes[recipeIndex] = currentRecipe.copy(
                ingredients = updatedIngredients,
                lastEditied = System.currentTimeMillis()
            )
            saveRecipesToStorage()
            return currentRecipe.uuid
        }
        return ""
    }

    fun setcurrentRecipeId(id: String) {
        _currentRecipeId.clear()
        _currentRecipeId.add(id)
    }

    fun addIngredientToRecipe(recipeId: String, name: String, quantity: Int) {
        val recipeIndex = _recipes.indexOfFirst { it.uuid == recipeId }
        if (recipeIndex != -1) {
            val currentRecipe = _recipes[recipeIndex]
            val newIngredient = RecipeIngredient(
                uuid = UUID.randomUUID().toString(),
                name = name,
                quantity = quantity.toString()
            )
            val updatedIngredients = currentRecipe.ingredients + newIngredient
            _recipes[recipeIndex] = currentRecipe.copy(
                ingredients = updatedIngredients,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveRecipesToStorage()
    }

    fun removeIngredientFromRecipe(recipeId: String, ingredientId: String) {
        val recipeIndex = _recipes.indexOfFirst { it.uuid == recipeId }
        if (recipeIndex != -1) {
            val currentRecipe = _recipes[recipeIndex]
            val updatedIngredients = currentRecipe.ingredients.filterNot { it.uuid == ingredientId }
            _recipes[recipeIndex] = currentRecipe.copy(
                ingredients = updatedIngredients,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveRecipesToStorage()
    }

    private suspend fun loadRecipes() {
        Log.d("RecipeStore", "Loading Recipes ...")
        withContext(Dispatchers.Main) {
            _isLoading.value = true
        }

        val loadedRecipes: List<Recipe> =
            DataUtils.loadFromStorage<List<Recipe>>(getApplication(), storeFileName)
                ?: emptyList()

        withContext(Dispatchers.Main) {
            _recipes.clear()
            _recipes.addAll(loadedRecipes)
            _isLoading.value = false
            Log.d("RecipeStore", "Loaded ${_recipes.size} Recipes")
        }
    }

    private fun saveRecipesToStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            DataUtils.saveToStorage(getApplication(), _recipes.toList(), storeFileName)
        }
    }
}

