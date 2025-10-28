package com.shroomlife.shliste

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import com.shroomlife.shliste.modules.lists.ListStore
import com.shroomlife.shliste.modules.lists.listNavGraph
import com.shroomlife.shliste.modules.recipes.recipeNavGraph
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
            androidx.lifecycle.ViewModelProvider(it)[ListStore::class.java]
        }
        listStore?.setAuthorizedList(null)
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
        listNavGraph(navController)
        recipeNavGraph(navController)
    }
}
