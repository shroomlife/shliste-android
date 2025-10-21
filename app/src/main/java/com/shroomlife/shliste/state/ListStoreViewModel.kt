package com.shroomlife.shliste.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.shroomlife.shliste.utils.ColorUtils.getRandomColor
import java.util.UUID

class ListStoreViewModel : ViewModel() {

    private val _lists = mutableStateListOf<Shliste>()
    val lists: List<Shliste> get() = _lists

    init {
    }

    fun addList(name: String) {
        _lists.add(Shliste(uuid = UUID.randomUUID().toString(), name = name, color = Color(getRandomColor())))
    }

    fun removeList(id: String) {
        _lists.removeAll { it.uuid == id }
    }

    fun getListById(id: String): Shliste? {
        return _lists.find { it.uuid == id }
    }

    fun addItemToList(listId: String, name: String) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items + ListItem(
                uuid = UUID.randomUUID().toString(),
                name = name
            )
            _lists[listIndex] = current.copy(items = updatedItems)
        }
    }

    fun toggleItemChecked(listId: String, itemId: String) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.map {
                if (it.uuid == itemId) it.copy(checked = !it.checked) else it
            }
            _lists[listIndex] = current.copy(items = updatedItems)
        }
    }

    fun removeItemFromList(listId: String, itemId: String) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.filterNot { it.uuid == itemId }
            _lists[listIndex] = current.copy(items = updatedItems)
        }
    }
}
