package com.shroomlife.shliste.modules.lists.screens

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
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.components.BackButton
import com.shroomlife.shliste.modules.lists.components.ListBottomBar
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.modules.lists.components.ListItem

@Composable
fun ListsDetailScreen(listId: String) {
    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current

    listStore.setCurrentListId(listId)
    val list = listStore.getCurrentList()

    val itemCount = list?.items?.size ?: 0

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
            BackButton()
        },
        bottomBar = {
            ListBottomBar()
        }
    ) {
        if(list != null) {
            ListCard(
                uuid = list.uuid,
                name = list.name,
                color = Color(
                    android.graphics.Color.parseColor(list.color)
                ),
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
        }
    }
}