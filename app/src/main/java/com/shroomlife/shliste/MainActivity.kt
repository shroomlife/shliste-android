package com.shroomlife.shliste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shroomlife.shliste.components.AppBottomBar
import com.shroomlife.shliste.components.AppHeader
import com.shroomlife.shliste.components.ListBottomBar
import com.shroomlife.shliste.ui.theme.ShlisteTheme
import com.shroomlife.shliste.screens.*
import com.shroomlife.shliste.state.BottomNavType

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

    val appStore = LocalAppStore.current
    val navController = LocalNavController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppHeader(navController)
        },
        bottomBar = {
            when (appStore.bottomNavType.value) {
                BottomNavType.APP -> AppBottomBar()
                BottomNavType.LIST -> ListBottomBar()
                BottomNavType.NONE -> {}
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NavHost(
                navController = navController,
                startDestination = AppDestinations.LISTS.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                composable(AppDestinations.LISTS.route) { ListsScreen() }
                composable(AppDestinations.RECIPES.route) { RecipesScreen() }
                composable(AppDestinations.PRODUCTS.route) { ProductsScreen() }
                composable(AppDestinations.STORES.route) { StoresScreen() }
                composable(
                    route = Routes.LIST_DETAIL,
                    arguments = listOf(navArgument("listId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val listId = backStackEntry.arguments?.getString("listId")
                    if (listId === null) {
                        navController.navigate(AppDestinations.LISTS.route)
                        return@composable
                    }
                    ListsDetail(listId = listId)
                }
            }
        }
    }
}
