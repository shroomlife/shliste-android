package com.shroomlife.shliste.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.SecondaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    navController: NavHostController
) {
    Column {
        TopAppBar(
            modifier = Modifier
                .clickable {
                    navigateTo(navController, Routes.LISTS, Routes.LISTS)
                }
            ,
            title = {
                Icon(
                    painter = painterResource(id = R.drawable.ci_brand),
                    contentDescription = "shliste",
                    modifier = Modifier
                        .width(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
        )
        HorizontalDivider(
            color = SecondaryColor
        )
    }
}