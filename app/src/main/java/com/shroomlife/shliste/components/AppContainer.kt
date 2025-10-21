package com.shroomlife.shliste.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalAppStore

@Composable
fun AppContainer(
    beforePadding: @Composable (() -> Unit)? = null,
    afterPadding: @Composable (() -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable () -> Unit
) {

    val appStore = LocalAppStore.current
    LaunchedEffect(scrollState) {
        appStore.updateScrollState(scrollState)
    }


    Column {
        if(beforePadding != null) {
            beforePadding()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            content()
        }

        if(afterPadding != null) {
            afterPadding()
        }
    }
}
