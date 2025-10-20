package com.shroomlife.shliste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shroomlife.shliste.AppDestinations
import com.shroomlife.shliste.LocalNavController

@Composable
fun AppBottomBar() {

    val iconSize = 32.dp
    val paddingBottom = 24.dp
    val paddingTop = 12.dp

    val navController = LocalNavController.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFDECF5)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (destination in AppDestinations.entries) {
                val isSelected = currentRoute?.startsWith(destination.route) == true
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate(destination.route)
                        }
                        .padding(bottom = paddingBottom, top = paddingTop),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.label,
                        modifier = Modifier.size(iconSize),
                        tint = if(isSelected) {
                            Color(0xFFE064B2)
                        } else {
                            Color(0xFF6B7280)
                        }
                    )
                }
            }
        }
    }
}
