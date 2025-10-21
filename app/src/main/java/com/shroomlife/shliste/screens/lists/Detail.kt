package com.shroomlife.shliste.screens.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.LocalAppStore
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.components.AppContainer
import com.shroomlife.shliste.state.BottomNavType

@Composable
fun ListsDetailScreen(listId: String) {
    val appStore = LocalAppStore.current
    val listStore = LocalListStore.current

    LaunchedEffect(Unit) {
        appStore.setBottomNav(BottomNavType.LIST)
    }

    val list = remember(listId) {
        listStore.getListById(listId)
    }

    AppContainer() {

        if(list != null) {

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column() {
                    // 🏷 Title section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        list.color,
                                        Color.White
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = list.name,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    HorizontalDivider()

                    // 📄 Content section
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Hallo Welt"
                        )
                    }
                }
            }

        }

    }
}