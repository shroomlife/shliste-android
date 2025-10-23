package com.shroomlife.shliste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.AppDestinations
import com.shroomlife.shliste.R
import com.shroomlife.shliste.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    navController: androidx.navigation.NavHostController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified
        ),
        modifier = Modifier
            .clickable {
                navigateTo(navController, AppDestinations.LISTS.route)
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFDECF5),
                        Color(0xCCFDECF5)
                    )
                )
        ),
        title = {
            Icon(
                painter = painterResource(id = R.drawable.ci_brand),
                contentDescription = "shliste",
                modifier = Modifier
                    .width(120.dp),
                tint = Color(0xFFE064B2)
            )
        },
    )
}