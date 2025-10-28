package com.shroomlife.shliste.modules.lists.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.AppContainer
import com.shroomlife.shliste.components.LightBadge
import com.shroomlife.shliste.components.PrimaryFloatingButton
import com.shroomlife.shliste.modules.lists.ListItem
import com.shroomlife.shliste.modules.lists.Shliste
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.modules.lists.components.ListCreateSheet
import com.shroomlife.shliste.modules.lists.components.ListItemEditSheet
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.PrimaryColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsOverviewScreen() {
    val navController = LocalNavController.current
    val listStore = LocalListStore.current
    val lists = listStore.lists.sortedByDescending { it.lastEditied }

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var showCreateSheet by remember { mutableStateOf(false) }
    val listCreateSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showCreateSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    listCreateSheetState.hide()
                    showCreateSheet = false
                }
            },
            sheetState = listCreateSheetState
        ) {
            ListCreateSheet(
                onSaved = { newListId ->
                    scope.launch {
                        listCreateSheetState.hide()
                        showCreateSheet = false
                        listState.scrollToItem(0)
                        navigateTo(navController, Routes.listDetail(newListId))
                    }
                }
            )
        }
    }

    AppContainer(
        floatingActionButton = {
            PrimaryFloatingButton(
                onClick = {
                    scope.launch {
                        showCreateSheet = true
                        listCreateSheetState.show()
                    }
                },
                caption = "Neue Liste"
            )
        },
        isLoading = listStore.isLoading,
        disableScroll = true,
        disablePadding = true
    ) {
        if (lists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Keine Listen vorhanden.")
            }
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(lists, key = { it.uuid }) { list ->
                    ListCard(
                        list = list,
                        onClick = { uuid ->
                            navigateTo(navController, Routes.listDetail(uuid))
                        }
                    ) {
                        LightBadge(
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.icon_products),
                                    contentDescription = "Einträge",
                                    tint = Color(0xFF111827),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        ) {
                            Text(
                                text = if (list.items.size == 1)
                                    "1 Eintrag"
                                else
                                    "${list.items.size} Einträge",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}