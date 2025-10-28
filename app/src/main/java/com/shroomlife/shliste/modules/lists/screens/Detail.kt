package com.shroomlife.shliste.modules.lists.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.AppContainer
import com.shroomlife.shliste.components.BackButton
import com.shroomlife.shliste.modules.lists.ListItem
import com.shroomlife.shliste.modules.lists.Shliste
import com.shroomlife.shliste.modules.lists.components.DeleteListButton
import com.shroomlife.shliste.modules.lists.components.ListBottomBar
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.modules.lists.components.ListEditSheet
import com.shroomlife.shliste.modules.lists.components.ListItem
import com.shroomlife.shliste.modules.lists.components.ListItemEditSheet
import com.shroomlife.shliste.navigateTo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsDetailScreen(listId: String) {

    val context = LocalContext.current
    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    val isDeleted = remember { mutableStateOf<Boolean>(false) }

    val scope = rememberCoroutineScope()

    var editingItem by remember { mutableStateOf<ListItem?>(null) }
    val listItemEditSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (editingItem != null) {
        ModalBottomSheet(
            onDismissRequest = { editingItem = null },
            sheetState = listItemEditSheetState
        ) {
            ListItemEditSheet(
                listItemId = editingItem!!.uuid,
                onSaved = {
                    scope.launch {
                        listItemEditSheetState.hide()
                        listItemEditSheetState.currentValue
                        editingItem = null
                    }
                }
            )
        }
    }

    var editingList by remember { mutableStateOf<Shliste?>(null) }
    val listEditSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (editingList != null) {
        ModalBottomSheet(
            onDismissRequest = { editingList = null },
            sheetState = listEditSheetState
        ) {
            ListEditSheet(
                listId = editingList!!.uuid,
                onSaved = {
                    scope.launch {
                        listEditSheetState.hide()
                        listEditSheetState.currentValue
                        editingList = null
                    }
                }
            )
        }
    }

    val list = listStore.lists.firstOrNull { it.uuid == listId }
    if (!listStore.isLoading && list == null && isDeleted.value == false) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Liste nicht gefunden", Toast.LENGTH_SHORT).show()
            navigateTo(navController, Routes.LISTS)
        }
        return
    }

    if(list == null) {
        return
    }

    val itemCount = list.items.size
    var previousCount by remember { mutableStateOf(itemCount) }
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(itemCount) {
        val state = appStore.scrollState ?: return@LaunchedEffect
        if (!initialized) {
            initialized = true
            previousCount = itemCount
            return@LaunchedEffect
        }
        if (itemCount > previousCount) {
            state.animateScrollTo(state.maxValue)
        }
        previousCount = itemCount
    }

    AppContainer(
        beforePadding = {
            BackButton(
                to = Routes.LISTS
            )
        },
        bottomBar = {
            ListBottomBar(listId)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListCard(
                list = list,
                onClickHeader = {
                    editingList = list
                }
            ) {
                if(list.items.isEmpty()) {
                    Text(
                        text = "Keine Einträge",
                        color = Color(0xFF6b7280)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for(item in list.items) {
                            ListItem(
                                listId = listId,
                                item = item,
                                onClick = { editingItem = item } // 👈 open sheet
                            )
                        }
                    }
                }
            }
            DeleteListButton(
                onRemoved = {
                    isDeleted.value = true
                    navigateTo(navController, Routes.LISTS, Routes.listDetail(listId))
                    listStore.removeList(listId)
                }
            )
        }
    }
}