package com.shroomlife.shliste.modules.lists

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.shroomlife.shliste.utils.ColorUtils
import java.util.UUID
import kotlin.collections.plus

class ListStore : ViewModel() {

    private val _lists = mutableStateListOf<Shliste>()
    private val _currentListId = mutableStateListOf<String?>()
    val lists: List<Shliste> get() = _lists

//    init {
//    }

    fun addList(name: String) {
        _lists.add(
            Shliste(
                uuid = UUID.randomUUID().toString(),
                name = name,
                color = Color(ColorUtils.getRandomColor())
            )
        )
    }

//    fun removeList(id: String) {
//        _lists.removeAll { it.uuid == id }
//    }

    fun getListById(id: String): Shliste? {
        return _lists.find { it.uuid == id }
    }

    fun getCurrentList(): Shliste? {
        val currentListId = _currentListId.firstOrNull() ?: return null
        return getListById(currentListId)
    }

    fun checkListItem(itemUuid: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        checkItemInList(currentListId, itemUuid, true)
    }

    fun uncheckListItem(itemUuid: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        checkItemInList(currentListId, itemUuid, false)
    }

    fun removeListItem(itemUuid: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        removeItemFromList(currentListId, itemUuid)
    }

    fun addItemToCurrentList(name: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        addItemToList(currentListId, name)
    }

    fun setCurrentListId(id: String) {
        _currentListId.clear()
        _currentListId.add(id)
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

    // a function without toggle
    fun checkItemInList(listId: String, itemId: String, checked: Boolean) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.map {
                if (it.uuid == itemId) it.copy(checked = checked) else it
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