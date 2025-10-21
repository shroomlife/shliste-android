package com.shroomlife.shliste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.shroomlife.shliste.components.AppBottomBar
import com.shroomlife.shliste.components.AppHeader
import com.shroomlife.shliste.components.ListBottomBar
import com.shroomlife.shliste.ui.theme.ShlisteTheme
import com.shroomlife.shliste.screens.lists.ListsCreateScreen
import com.shroomlife.shliste.screens.lists.ListsDetailScreen
import com.shroomlife.shliste.screens.lists.ListsOverviewScreen
import com.shroomlife.shliste.screens.products.ProductsOverviewScreen
import com.shroomlife.shliste.screens.recipes.RecipesOverviewScreen
import com.shroomlife.shliste.screens.stores.StoresOverviewScreen
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

@Composable
fun currentBottomBarForDestination(navController: NavHostController): BottomNavType {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    return when (route) {
        Routes.LISTS -> BottomNavType.APP
        Routes.LIST_DETAIL -> BottomNavType.LIST
        else -> BottomNavType.NONE
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
            when (currentBottomBarForDestination(navController)) {
                BottomNavType.APP -> AppBottomBar()
                BottomNavType.LIST -> ListBottomBar()
                BottomNavType.NONE -> {}
            }
        },
        floatingActionButton = {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            when (appStore.bottomNavType.value) {
                BottomNavType.APP -> {
                    when (currentRoute) {
                        "lists" -> {
                            ExtendedFloatingActionButton(
                                onClick = {
                                    navController.navigate(Routes.LIST_CREATE)
                                },
                                containerColor = Color(0xFFE064B2),
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_plus),
                                        contentDescription = "Neue Liste",
                                        tint = Color(0xFFFFFFFF),
                                        modifier = Modifier.width(24.dp)
                                    )
                                },
                                text = { Text(text = "Neue Liste", color = Color(0xFFFFFFFF)) },
                            )
                        }
                        else -> {}
                    }
                }
                else -> {}
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
                        navController.navigate(Routes.LISTS)
                        return@composable
                    }
                    ListsDetailScreen(listId = listId)
                }
                composable(Routes.RECIPES) { RecipesOverviewScreen() }
                composable(Routes.PRODUCTS) { ProductsOverviewScreen() }
                composable(Routes.STORES) { StoresOverviewScreen() }
            }
        }
    }
}
