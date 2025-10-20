package com.shroomlife.shliste.screens

import android.util.Log
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.components.ListCard
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsScreen() {

    val navController = LocalNavController.current
    val appStore = LocalAppStore.current

    val listStore = LocalListStore.current
    val lists = listStore.lists

    LaunchedEffect(Unit) {
        appStore.setBottomNav(BottomNavType.APP)
    }

    AppContainer() {

        FloatingActionButton(
            onClick = {
                Log.d("Clicked", "Floating Button")
            },
        ) {
            Text("Neu")
        }

        for(list in lists) {
            ListCard(
                uuid = list.id,
                title = list.name,
                content = "Andere",
                color = list.color,
                onClick = { uuid ->
                    navController.navigate("lists/$uuid")
                }
            )
        }
    }
}
