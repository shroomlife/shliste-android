package com.shroomlife.shliste.modules.lists

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListStoreFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStore::class.java)) {
            return ListStore(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
