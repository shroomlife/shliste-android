package com.shroomlife.shliste.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalNavController

@Composable
fun AppContainer(
    beforePadding: @Composable (() -> Unit)? = null,
    afterPadding: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    disableScroll: Boolean = false,
    content: @Composable () -> Unit
) {

    val appStore = LocalAppStore.current
    val navController = LocalNavController.current

    LaunchedEffect(scrollState) {
        appStore.updateScrollState(scrollState)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppHeader(navController)
        },
        bottomBar = {
            if(bottomBar != null) {
                bottomBar()
            } else {
                AppBottomBar()
            }
        },
        floatingActionButton = {
            if(floatingActionButton != null) {
                floatingActionButton()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            beforePadding?.invoke()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .then(
                        if(disableScroll) {
                            Modifier
                        } else {
                            Modifier
                                .verticalScroll(scrollState)
                        }
                    )
                    .padding(16.dp)
            ) {
                content.invoke()
            }
            afterPadding?.invoke()
        }
    }





}
