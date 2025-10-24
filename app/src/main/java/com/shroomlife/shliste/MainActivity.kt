package com.shroomlife.shliste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.modules.lists.screens.ListEditScreen
import com.shroomlife.shliste.modules.lists.screens.ListItemEditScreen
import com.shroomlife.shliste.ui.theme.ShlisteTheme
import com.shroomlife.shliste.modules.lists.screens.ListsCreateScreen
import com.shroomlife.shliste.modules.lists.screens.ListsDetailScreen
import com.shroomlife.shliste.modules.lists.screens.ListsOverviewScreen
import com.shroomlife.shliste.modules.recipes.screens.RecipesOverviewScreen

class MainActivity : ComponentActivity() {
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShlisteApp() {

    val navController = LocalNavController.current

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
            ListsDetailScreen(listId = listId)
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
            ListEditScreen(listId = listId)
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
            ListItemEditScreen(listItemId)
        }

        // Recipes
        composable(Routes.RECIPES) { RecipesOverviewScreen() }
    }
}
