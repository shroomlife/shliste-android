package com.shroomlife.shliste

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.FragmentActivity
import com.shroomlife.shliste.ui.theme.ShlisteTheme

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
            val protectedRoutes = listOf(
                Routes.LIST_DETAIL,
                Routes.LIST_EDIT,
                Routes.LIST_ITEM_EDIT
            )
            if (route != null && !protectedRoutes.any { route.startsWith(it) }) {
                if(listStore.hasAuthorizedList) {
                    listStore.setAuthorizedList(null)
                    protectedRoutes.forEach { protectedRoute ->
                        while (navController.popBackStack(protectedRoute, inclusive = true)) {
                            // wiederholen, falls mehrfach im Stack
                        }
                    }
                }
            }
        }
    }

    AppNavHost(navController)
}
