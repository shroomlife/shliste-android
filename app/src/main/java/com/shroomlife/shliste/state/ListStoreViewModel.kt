package com.shroomlife.shliste.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.shroomlife.shliste.utils.ColorUtils.getColorFromString
import com.shroomlife.shliste.utils.ColorUtils.getRandomColor
import java.util.UUID

class ListStoreViewModel : ViewModel() {

    private val _lists = mutableStateListOf<Shliste>()
    val lists: List<Shliste> get() = _lists

    init {
        addList("🛒🛒🛒")
        addList("Bae ♥️♥️♥️")
        addList("🎬🎬🎬")
        addList("Aufgaben 🧮")
    }

    fun addList(name: String) {
        _lists.add(Shliste(id = UUID.randomUUID().toString(), name = name, color = Color(getRandomColor())))
    }

    fun removeList(id: String) {
        _lists.removeAll { it.id == id }
    }

    fun addItemToList(listId: String, name: String) {
        val listIndex = _lists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items + ListItem(
                id = UUID.randomUUID().toString(),
                name = name
            )
            _lists[listIndex] = current.copy(items = updatedItems)
        }
    }

    fun toggleItemChecked(listId: String, itemId: String) {
        val listIndex = _lists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.map {
                if (it.id == itemId) it.copy(checked = !it.checked) else it
            }
            _lists[listIndex] = current.copy(items = updatedItems)
        }
    }

    fun removeItemFromList(listId: String, itemId: String) {
        val listIndex = _lists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.filterNot { it.id == itemId }
            _lists[listIndex] = current.copy(items = updatedItems)
        }
    }
}
