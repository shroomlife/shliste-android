package com.shroomlife.shliste

import android.app.Application
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shroomlife.shliste.state.AppStoreViewModel
import com.shroomlife.shliste.modules.lists.ListStore
import com.shroomlife.shliste.modules.lists.ListStoreFactory
import com.shroomlife.shliste.modules.recipes.RecipeStore
import com.shroomlife.shliste.modules.recipes.RecipeStoreFactory
import com.shroomlife.shliste.state.AppStoreFactory

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController")
}

val LocalAppStore = staticCompositionLocalOf<AppStoreViewModel> {
    error("No AppStore")
}

val LocalListStore = staticCompositionLocalOf<ListStore> {
    error("No ListStore")
}

val LocalRecipeStore = staticCompositionLocalOf<RecipeStore> {
    error("No RecipeStore")
}

@Composable
fun AppProvider(
    content: @Composable () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val application = context.applicationContext as Application

    val appStore: AppStoreViewModel = viewModel(
        factory = AppStoreFactory(application)
    )

    val listStore: ListStore = viewModel(
        factory = ListStoreFactory(application)
    )

    val recipeStore: RecipeStore = viewModel(
        factory = RecipeStoreFactory(application)
    )

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalAppStore provides appStore,
        LocalListStore provides listStore,
        LocalRecipeStore provides recipeStore,
        LocalActivity provides activity
    ) {
        content()
    }
}
