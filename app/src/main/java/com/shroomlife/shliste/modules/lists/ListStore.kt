package com.shroomlife.shliste.modules.lists

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shroomlife.shliste.modules.recipes.Recipe
import com.shroomlife.shliste.utils.ColorUtils
import com.shroomlife.shliste.utils.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.collections.plus

class ListStore(application: Application) : AndroidViewModel(application) {

    private val _isLoading = mutableStateOf(true)
    private val _lists = mutableStateListOf<Shliste>()
    private var authorizedListId by mutableStateOf<String?>(null)
    private val storeFileName = "lists.json"

    val lists: List<Shliste> get() = _lists
    val hasAuthorizedList: Boolean get() = authorizedListId != null
    val isLoading: Boolean get() = _isLoading.value

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadLists()
        }
    }

    private suspend fun loadLists() {
        Log.d("ListStore", "Loading Lists ...")
        withContext(Dispatchers.Main) {
            _isLoading.value = true
        }

        val loadedLists: List<Shliste> =
            DataUtils.loadFromStorage<List<Shliste>>(getApplication(), storeFileName)
                ?: emptyList()

        withContext(Dispatchers.Main) {
            _lists.clear()
            _lists.addAll(loadedLists)
            _isLoading.value = false
            Log.d("ListStore", "Loaded ${_lists.size} Lists")
        }
    }

    private fun saveListsToStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            DataUtils.saveToStorage(getApplication(), _lists.toList(), storeFileName)
        }
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
        if (isSecret) setAuthorizedList(newListId)
        saveListsToStorage()
    }

    fun removeList(id: String) {
        _lists.removeAll { it.uuid == id }
        saveListsToStorage()
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

    fun checkListItem(listId: String, itemUuid: String) {
        checkItemInList(listId, itemUuid, true)
        saveListsToStorage()
    }

    fun uncheckListItem(listId: String, itemUuid: String) {
        checkItemInList(listId, itemUuid, false)
        saveListsToStorage()
    }

    fun removeListItem(listId: String, itemUuid: String) {
        removeItemFromList(listId, itemUuid)
        saveListsToStorage()
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
        saveListsToStorage()
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
        saveListsToStorage()
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
            saveListsToStorage()
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
            saveListsToStorage()
            return currentList.uuid
        }
        return ""
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
        saveListsToStorage()
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
        saveListsToStorage()
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
        saveListsToStorage()
    }

}
