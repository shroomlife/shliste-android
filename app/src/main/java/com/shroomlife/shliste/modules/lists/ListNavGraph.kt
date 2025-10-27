package com.shroomlife.shliste.modules.lists

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.modules.lists.components.SecretListWrapper
import com.shroomlife.shliste.modules.lists.screens.ListEditScreen
import com.shroomlife.shliste.modules.lists.screens.ListItemEditScreen
import com.shroomlife.shliste.modules.lists.screens.ListsCreateScreen
import com.shroomlife.shliste.modules.lists.screens.ListsDetailScreen
import com.shroomlife.shliste.modules.lists.screens.ListsOverviewScreen
import com.shroomlife.shliste.navigateTo

fun NavGraphBuilder.listNavGraph(navController: NavController) {
    composable(Routes.LISTS) { ListsOverviewScreen() }
    composable(Routes.LIST_CREATE) { ListsCreateScreen() }
    composable(
        route = Routes.LIST_DETAIL,
        arguments = listOf(navArgument("listId") { type = NavType.StringType })
    ) { backStackEntry ->
        val listId = backStackEntry.arguments?.getString("listId")
        if (listId === null) {
            navigateTo(navController, Routes.LISTS)
            return@composable
        }
        SecretListWrapper(listId = listId) {
            ListsDetailScreen(listId = listId)
        }
    }
    composable(
        route = Routes.LIST_EDIT,
        arguments = listOf(navArgument("listId") { type = NavType.StringType })
    ) { backStackEntry ->
        val listId = backStackEntry.arguments?.getString("listId")
        if (listId === null) {
            navigateTo(navController, Routes.LISTS)
            return@composable
        }
        SecretListWrapper(listId = listId) {
            ListEditScreen(listId = listId)
        }
    }
    composable(
        route = Routes.LIST_ITEM_EDIT,
        arguments = listOf(navArgument("listItemId") { type = NavType.StringType })
    ) { backStackEntry ->
        val listItemId = backStackEntry.arguments?.getString("listItemId")
        if (listItemId === null) {
            navigateTo(navController, Routes.LISTS)
            return@composable
        }
        val listId = LocalListStore.current.getListIdByListItemId(listItemId)
        if (listId === null) {
            navigateTo(navController, Routes.LISTS)
            return@composable
        }
        SecretListWrapper(listId) {
            ListItemEditScreen(listItemId)
        }
    }
}