package com.shroomlife.shliste

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shroomlife.shliste.state.AppStoreViewModel
import com.shroomlife.shliste.state.ListStoreViewModel

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController provided")
}

val LocalAppStore = staticCompositionLocalOf<AppStoreViewModel> {
    error("No AppStore provided")
}

val LocalListStore = staticCompositionLocalOf<ListStoreViewModel> {
    error("No ListStore provided")
}

@Composable
fun AppProvider(
    content: @Composable () -> Unit
) {
    val navController = rememberNavController()
    val appStore: AppStoreViewModel = viewModel()
    val listStore: ListStoreViewModel = viewModel()

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalAppStore provides appStore,
        LocalListStore provides listStore
    ) {
        content()
    }
}
