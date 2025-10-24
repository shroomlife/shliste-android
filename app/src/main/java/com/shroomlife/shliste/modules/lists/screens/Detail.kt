package com.shroomlife.shliste.modules.lists.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.shroomlife.shliste.modules.lists.components.DeleteListButton
import com.shroomlife.shliste.modules.lists.components.ListBottomBar
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.modules.lists.components.ListItem
import com.shroomlife.shliste.navigateTo

@Composable
fun ListsDetailScreen(listId: String) {

    val context = LocalContext.current
    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    val isDeleted = remember { mutableStateOf<Boolean>(false) }

    listStore.setCurrentListId(listId)
    val list = listStore.getCurrentList()
    if(list == null) {
        Log.d("ListsDetailScreen", "isDeleted: $isDeleted")
        if(isDeleted.value == true) return
        Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show()
        navigateTo(navController, Routes.LISTS, Routes.listDetail(listId))
        return
    }

    val itemCount = list.items.size

    var previousCount by remember { mutableStateOf(itemCount) }
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(itemCount) {
        if (!initialized) {
            initialized = true
            previousCount = itemCount
            return@LaunchedEffect
        }
        if (itemCount > previousCount) {
            val state = appStore.scrollState ?: return@LaunchedEffect
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
            ListBottomBar()
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListCard(
                uuid = list.uuid,
                name = list.name,
                color = list.color,
                onClickHeader = {
                    navigateTo(navController, Routes.listEdit(listId))
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
                            ListItem(item = item)
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