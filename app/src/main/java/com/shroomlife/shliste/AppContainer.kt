package com.shroomlife.shliste

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.components.AppBottomBar
import com.shroomlife.shliste.components.AppHeader

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
            Column(
                modifier = Modifier
                    .imePadding()
                    .windowInsetsPadding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)
                    )
            ) {
                if(bottomBar != null) {
                    bottomBar()
                } else {
                    AppBottomBar()
                }
            }
        },
        floatingActionButton = {
            if(floatingActionButton != null) {
                floatingActionButton()
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
            .only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
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
