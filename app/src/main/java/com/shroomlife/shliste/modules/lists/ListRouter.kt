package com.shroomlife.shliste.modules.lists

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.modules.lists.components.SecretListWrapper
import com.shroomlife.shliste.modules.lists.screens.ListsDetailScreen
import com.shroomlife.shliste.modules.lists.screens.ListsOverviewScreen
import com.shroomlife.shliste.navigateTo

fun NavGraphBuilder.ListRouter(navController: NavController) {
    composable(Routes.LISTS) { ListsOverviewScreen() }
    composable(
        route = Routes.LIST_DETAIL,
        arguments = listOf(navArgument("listId") { type = NavType.StringType })
    ) { backStackEntry ->
        val listId = backStackEntry.arguments?.getString("listId")
        if (listId === null) {
            navigateTo(navController, Routes.LISTS, Routes.LISTS)
            return@composable
        }
        SecretListWrapper(listId = listId) {
            ListsDetailScreen(listId = listId)
        }
    }
}