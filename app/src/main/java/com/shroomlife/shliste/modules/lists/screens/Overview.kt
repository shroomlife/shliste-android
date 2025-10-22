package com.shroomlife.shliste.modules.lists.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.components.LightBadge
import com.shroomlife.shliste.modules.lists.components.ListCard
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsOverviewScreen() {

    val navController = LocalNavController.current
    val listStore = LocalListStore.current

    val lists = listStore.lists.sortedByDescending {
        it.lastEditied
    }

    LaunchedEffect(Unit) {
    }

    AppContainer(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(Routes.LIST_CREATE)
                },
                containerColor = Color(0xFFE064B2),
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.icon_plus),
                        contentDescription = "Neue Liste",
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier.width(24.dp)
                    )
                },
                text = { Text(text = "Neue Liste", color = Color(0xFFFFFFFF)) },
            )
        }
    ) {

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
                        color = Color(
                            android.graphics.Color.parseColor(list.color)
                        ),
                        onClick = { uuid ->
                            navController.navigate("lists/$uuid")
                        }
                    ) {
                        LightBadge(
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.icon_products),
                                    contentDescription = "Einträge",
                                    tint = Color(0xFF111827),
                                    modifier = Modifier
                                        .size(14.dp)
                                )
                            }
                        ) {
                            Text("${list.items.size} Einträge")
                        }
                    }
                }
            }
        }

    }
}
