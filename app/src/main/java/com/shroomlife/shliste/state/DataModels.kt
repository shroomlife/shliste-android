package com.shroomlife.shliste.state

import androidx.compose.ui.graphics.Color

data class ListItem(
    val id: String,
    val name: String,
    val checked: Boolean = false
)

data class Shliste(
    val id: String,
    val name: String,
    val color: Color,
    val items: List<ListItem> = emptyList()
)
