package com.shroomlife.shliste.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsDetail(listId: String) {

    val appStore = LocalAppStore.current
    LaunchedEffect(Unit) {
        appStore.setBottomNav(BottomNavType.LIST)
    }

    AppContainer() {
        Text("Hallo $listId")
    }
}
