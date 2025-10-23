package com.shroomlife.shliste.modules.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.LocalNavController
import com.shroomlife.shliste.R
import com.shroomlife.shliste.Routes
import com.shroomlife.shliste.modules.lists.ListItem
import com.shroomlife.shliste.navigateTo
import com.shroomlife.shliste.ui.theme.PrimaryColor
import com.shroomlife.shliste.ui.theme.SecondaryColor
import com.shroomlife.shliste.ui.theme.ZainFontFamily

@Composable
fun ListItem(
    item: ListItem
) {
    val listStore = LocalListStore.current
    val navController = LocalNavController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if(item.quantity > 1) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            color = if(item.checked) {
                                Color.LightGray
                            } else {
                                SecondaryColor
                            },
                            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                        )
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${item.quantity}x",
                        fontSize = 20.sp,
                        color = if(item.checked) {
                            Color.Gray
                        } else {
                            PrimaryColor
                        },
                        fontWeight = FontWeight.Bold,
                        fontFamily = ZainFontFamily
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navigateTo(
                            navController,
                            Routes.listItemEdit(item.uuid)
                        )
                    }
                    .padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    )
                    .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item.name,
                    fontSize = 20.sp,
                    color = if(item.checked) {
                        Color.LightGray
                    } else {
                        Color.Black
                    }
                )
            }

            if(item.checked) {
                Row() {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(
                                color = Color(0xFFfff7ed)
                            )
                            .clickable { listStore.uncheckListItem(item.uuid) }
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_undo),
                            contentDescription = "Rückgängig",
                            tint = Color(0xFFf97316),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(
                                color = Color(0xFFfdecf5),
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )
                            .clickable { listStore.removeListItem(item.uuid) }
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_trash),
                            contentDescription = "Löschen",
                            tint = Color(0xFFf53f96),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            color = Color(0xFFeffaf5),
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        )
                        .clickable { listStore.checkListItem(item.uuid) }
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_check),
                        contentDescription = "Abhaken",
                        tint = Color(0xFF2d9c74),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }

    }






/*
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // 👈 hier!
        ) {
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                textDecoration = if (item.checked) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )

            if (item.checked) {
                Row {
                    // Undo-Button
                    Box(
                        modifier = Modifier
                            .background(color = Color(0xFFfff7ed))
                            .clickable { listStore.uncheckListItem(item.uuid) }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_undo),
                            contentDescription = "Rückgängig",
                            tint = Color(0xFFf97316),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    // Delete-Button
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFfdecf5),
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                            )
                            .clickable { listStore.removeListItem(item.uuid) }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_trash),
                            contentDescription = "Löschen",
                            tint = Color(0xFFf53f96),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFeffaf5),
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        )
                        .clickable { listStore.checkListItem(item.uuid) }
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_check),
                        contentDescription = "Abhaken",
                        tint = Color(0xFF2d9c74),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
    */
}
