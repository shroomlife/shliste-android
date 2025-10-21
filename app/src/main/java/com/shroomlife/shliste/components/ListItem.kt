package com.shroomlife.shliste.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.shroomlife.shliste.LocalListStore
import com.shroomlife.shliste.R
import com.shroomlife.shliste.state.ListItem

@Composable
fun ListItem(
    item: ListItem
) {

    val listStore = LocalListStore.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Row() {
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                textDecoration = if(item.checked) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )


            if(item.checked) {
                Row() {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFfff7ed),
                            )
                            .clickable {
                                listStore.uncheckListItem(item.uuid)
                            }
                            .align(Alignment.CenterVertically)
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
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFfdecf5),
                                shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
                            )
                            .clickable {
                                listStore.removeListItem(item.uuid)
                            }
                            .align(Alignment.CenterVertically)
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
                            shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
                        )
                        .clickable {
                            listStore.checkListItem(item.uuid)
                        }
                        .align(Alignment.CenterVertically)
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
}