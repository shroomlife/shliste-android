package com.shroomlife.shliste

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.modules.lists.components.SecretListWrapper
import com.shroomlife.shliste.modules.lists.screens.ListEditScreen
import com.shroomlife.shliste.modules.lists.screens.ListItemEditScreen
import com.shroomlife.shliste.ui.theme.ShlisteTheme
import com.shroomlife.shliste.modules.lists.screens.ListsCreateScreen
import com.shroomlife.shliste.modules.lists.screens.ListsDetailScreen
import com.shroomlife.shliste.modules.lists.screens.ListsOverviewScreen
import com.shroomlife.shliste.modules.recipes.screens.RecipesOverviewScreen

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShlisteTheme {
                AppProvider {
                    ShlisteApp()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val listStore = (this as? FragmentActivity)?.let {
            androidx.lifecycle.ViewModelProvider(it)[com.shroomlife.shliste.modules.lists.ListStore::class.java]
        }
        listStore?.setAuthorizedList(null)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShlisteApp() {

    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            val route = backStackEntry.destination.route
            val allowedRoutes = listOf(
                Routes.LIST_DETAIL,
                Routes.LIST_EDIT,
                Routes.LIST_ITEM_EDIT
            )
            if (route != null && !allowedRoutes.any { route.startsWith(it) }) {
                listStore.setAuthorizedList(null)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LISTS,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {

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

        // Recipes
        composable(Routes.RECIPES) { RecipesOverviewScreen() }
    }
}
