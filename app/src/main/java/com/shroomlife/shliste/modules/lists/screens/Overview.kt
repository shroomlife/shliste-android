package com.shroomlife.shliste.modules.lists.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.PrimaryColor

@Composable
fun ListsOverviewScreen() {
    val navController = LocalNavController.current
    val listStore = LocalListStore.current
    val lists = listStore.lists.sortedByDescending { it.lastEditied }

    AppContainer(
        floatingActionButton = {
            PrimaryFloatingButton(
                to = Routes.LIST_CREATE,
                caption = "Neue Liste"
            )
        },
        isLoading = listStore.isLoading,
        disableScroll = true
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(lists, key = { it.uuid }) { list ->
                    ListCard(
                        uuid = list.uuid,
                        name = list.name,
                        color = list.color,
                        secret = list.secret,
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