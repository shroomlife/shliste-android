package com.shroomlife.shliste.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.AppContainer
import com.shroomlife.shliste.R
import com.shroomlife.shliste.ui.theme.ZainFontFamily

@Composable
fun BiometricLoadingScreen() {
    AppContainer(
        bottomBar = {},
        disableScroll = true,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_biometrics),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(128.dp)
            )
            Text("Warte auf Authentifizierung", fontFamily = ZainFontFamily, fontWeight = FontWeight.Bold)
        }
    }
}