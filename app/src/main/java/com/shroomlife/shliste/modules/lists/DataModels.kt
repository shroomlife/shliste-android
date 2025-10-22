package com.shroomlife.shliste.modules.lists

import kotlinx.serialization.*

@Serializable
data class ListItem(
    val uuid: String,
    val name: String,
    val checked: Boolean = false
)

@Serializable
data class Shliste(
    val uuid: String,
    val name: String,
    val color: String,
    val items: List<ListItem> = emptyList(),
    val lastEditied: Long = System.currentTimeMillis()
)
