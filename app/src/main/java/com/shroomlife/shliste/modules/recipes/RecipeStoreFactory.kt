package com.shroomlife.shliste.modules.recipes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shroomlife.shliste.modules.lists.ListStore

class RecipeStoreFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeStore::class.java)) {
            return RecipeStore(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
