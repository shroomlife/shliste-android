package com.shroomlife.shliste.modules.lists.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.components.BiometricLoadingScreen
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.utils.AppUtils

@Composable
fun SecretListWrapper(
    listId: String,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current
    val navController = LocalNavController.current
    val activity = context as? FragmentActivity

    val isSecret = remember { listStore.getIsListSecret(listId) }
    var authorized by remember { mutableStateOf(false) }

    LaunchedEffect(isSecret, authorized) {
        if (isSecret && !authorized) {
            if (activity == null) {
                Toast.makeText(context, "Keine Activity gefunden", Toast.LENGTH_SHORT).show()
                navigateTo(navController, Routes.LISTS, Routes.LISTS)
                return@LaunchedEffect
            }
            if (appStore.isBiometricAvailable()) {
                AppUtils.authenticateWithBiometrics(
                    activity,
                    onSuccess = {
                        authorized = true
                    },
                    onError = {
                        Toast.makeText(context, "Fehlende Authentifizierung", Toast.LENGTH_SHORT).show()
                        navigateTo(navController, Routes.LISTS, Routes.LISTS)
                    }
                )
            } else {
                Toast.makeText(context, "Biometrische Authentifizierung nicht verfügbar", Toast.LENGTH_SHORT).show()
                navigateTo(navController, Routes.LISTS, Routes.LISTS)
            }
        }
    }

    if (!isSecret || authorized) {
        content()
    } else {
        BiometricLoadingScreen()
    }
}
