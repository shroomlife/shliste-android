package com.shroomlife.shliste.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.components.ListCard
import com.shroomlife.shliste.components.ListItem
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsDetailScreen(listId: String) {
    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        listStore.setCurrentListId(listId)
        appStore.setBottomNav(BottomNavType.LIST)
    }

    val list = listStore.getCurrentList()
    LaunchedEffect(list?.items?.size, appStore.scrollState) {
        val state = appStore.scrollState ?: return@LaunchedEffect
        state.animateScrollTo(state.maxValue)
    }

    AppContainer(
        beforePadding = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable(enabled = true) {
                        navController.popBackStack()
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_arrow_left),
                    contentDescription = "Zurück",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Zurück zur Übersicht"
                )
            }

            HorizontalDivider()
        }
    ) {
        if(list != null) {
            ListCard(
                uuid = list.uuid,
                name = list.name,
                color = list.color,
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