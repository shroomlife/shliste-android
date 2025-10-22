package com.shroomlife.shliste.modules.lists.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsOverviewScreen() {

    val navController = LocalNavController.current
    val appStore = LocalAppStore.current

    val listStore = LocalListStore.current
    val lists = listStore.lists

    LaunchedEffect(Unit) {
        appStore.setBottomNav(BottomNavType.APP)
    }

    AppContainer() {

        if(lists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Keine Listen vorhanden.")
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                for (list in lists) {
                    ListCard(
                        uuid = list.uuid,
                        name = list.name,
                        color = list.color,
                        onClick = { uuid ->
                            navController.navigate("lists/$uuid")
                        }
                    ) {
                        Row() {
                            Text(
                                text = "${list.items.size} Einträge",
                                color = Color(0xFF6b7280)
                            )
                        }
                    }
                }
            }
        }

    }
}