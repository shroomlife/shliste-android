package com.shroomlife.shliste

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shroomlife.shliste.modules.lists.listNavGraph
import com.shroomlife.shliste.modules.recipes.recipeNavGraph


@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LISTS,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        listNavGraph(navController)
        recipeNavGraph(navController)
    }
}