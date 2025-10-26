package com.shroomlife.shliste.modules.lists

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.shroomlife.shliste.utils.ColorUtils
import java.io.File
import java.util.UUID
import kotlin.collections.plus
import kotlinx.serialization.json.*

class ListStore(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _lists = mutableStateListOf<Shliste>()
    private var authorizedListId by mutableStateOf<String?>(null)
    private val _currentListId = mutableStateListOf<String?>()
    val lists: List<Shliste> get() = _lists

    init {
        loadListsFromStorage(context)
    }

    fun isListAuthorized(listId: String): Boolean {
        return authorizedListId == listId
    }

    fun setAuthorizedList(listId: String?) {
        authorizedListId = listId
    }

    fun addList(name: String, isSecret: Boolean = false) {
        val newListId = UUID.randomUUID().toString()
        _lists.add(
            Shliste(
                uuid = newListId,
                name = name,
                color = ColorUtils.getRandomColor(),
                secret = isSecret,
            )
        )
        if(isSecret) {
            setAuthorizedList(newListId)
        }
        saveListsToStorage(context)
    }

    fun removeList(id: String) {
        _lists.removeAll { it.uuid == id }
        saveListsToStorage(context)
    }

    fun getListById(id: String): Shliste? {
        return _lists.find { it.uuid == id }
    }

    fun getIsListSecret(listId: String): Boolean {
        val list = getListById(listId) ?: throw Exception("List not found")
        return list.secret
    }

    fun getListItemById(itemId: String): ListItem? {
        _lists.forEach { list ->
            list.items.forEach { item ->
                if (item.uuid == itemId) {
                    return item
                }
            }
        }
        return null
    }

    fun getListIdByListItemId(itemId: String): String? {
        _lists.forEach { list ->
            list.items.forEach { item ->
                if (item.uuid == itemId) {
                    return list.uuid
                }
            }
        }
        return null
    }

    fun getCurrentList(): Shliste? {
        val currentListId = _currentListId.firstOrNull() ?: return null
        return getListById(currentListId)
    }

    fun checkListItem(itemUuid: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        checkItemInList(currentListId, itemUuid, true)
        saveListsToStorage(context)
    }

    fun uncheckListItem(itemUuid: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        checkItemInList(currentListId, itemUuid, false)
        saveListsToStorage(context)
    }

    fun removeListItem(itemUuid: String) {
        val currentListId = _currentListId.firstOrNull() ?: return
        removeItemFromList(currentListId, itemUuid)
        saveListsToStorage(context)
    }

    fun addItemToCurrentList(name: String, quantity: Int = 1) {
        val currentListId = _currentListId.firstOrNull() ?: return
        addItemToList(currentListId, name, quantity)
    }

    fun updateListName(listId: String, newName: String) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            _lists[listIndex] = current.copy(
                name = newName,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveListsToStorage(context)
    }

    fun updateListSecretState(listId: String, isSecret: Boolean) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            _lists[listIndex] = current.copy(
                secret = isSecret,
                lastEditied = System.currentTimeMillis()
            )
            setAuthorizedList(listId)
        }
        saveListsToStorage(context)
    }

    fun updateListItemName(listItemId: String, newName: String): String {
        val listIndex = _lists.indexOfFirst { list ->
            list.items.any { it.uuid == listItemId }
        }
        if (listIndex != -1) {
            val currentList = _lists[listIndex]
            val updatedItems = currentList.items.map { item ->
                if (item.uuid == listItemId) {
                    item.copy(
                        name = newName
                    )
                } else item
            }

            _lists[listIndex] = currentList.copy(
                items = updatedItems,
                lastEditied = System.currentTimeMillis()
            )
            saveListsToStorage(context)
            return currentList.uuid
        }
        return ""
    }

    fun updateListItemQuantity(listItemId: String, newQuantity: Int): String {
        val listIndex = _lists.indexOfFirst { list ->
            list.items.any { it.uuid == listItemId }
        }
        if (listIndex != -1) {
            val currentList = _lists[listIndex]
            val updatedItems = currentList.items.map { item ->
                if (item.uuid == listItemId) {
                    item.copy(
                        quantity = newQuantity
                    )
                } else item
            }

            _lists[listIndex] = currentList.copy(
                items = updatedItems,
                lastEditied = System.currentTimeMillis()
            )
            saveListsToStorage(context)
            return currentList.uuid
        }
        return ""
    }

    fun setCurrentListId(id: String) {
        _currentListId.clear()
        _currentListId.add(id)
    }

    fun addItemToList(listId: String, name: String, quantity: Int = 1) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items + ListItem(
                uuid = UUID.randomUUID().toString(),
                name = name,
                quantity = quantity
            )
            _lists[listIndex] = current.copy(
                items = updatedItems,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveListsToStorage(context)
    }

    fun checkItemInList(listId: String, itemId: String, checked: Boolean) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.map {
                if (it.uuid == itemId) it.copy(checked = checked) else it
            }
            _lists[listIndex] = current.copy(
                items = updatedItems,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveListsToStorage(context)
    }

    fun removeItemFromList(listId: String, itemId: String) {
        val listIndex = _lists.indexOfFirst { it.uuid == listId }
        if (listIndex != -1) {
            val current = _lists[listIndex]
            val updatedItems = current.items.filterNot { it.uuid == itemId }
            _lists[listIndex] = current.copy(
                items = updatedItems,
                lastEditied = System.currentTimeMillis()
            )
        }
        saveListsToStorage(context)
    }

    fun loadListsFromStorage(context: Context) {
        try {
            val file = getStorageFile(context)
            if (file.exists()) {
                val json = file.readText()
                val loadedLists = Json.decodeFromString<List<Shliste>>(json)
                _lists.clear()
                _lists.addAll(loadedLists)
            }
            Log.d("ListStore", "Lists loaded from storage.")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveListsToStorage(context: Context) {
        try {
            val json = Json.encodeToString(_lists.toList())
            val file = getStorageFile(context)
            file.writeText(json)
            Log.d("ListStore", "Lists saved to storage.")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun getStorageFile(context: Context): File {
    return File(context.filesDir, "lists.json")
}
