package com.shroomlife.shliste.modules.lists

import kotlinx.serialization.*

@Serializable
data class ListItem(
    val uuid: String,
    val name: String,
    val quantity: Int = 1,
    val checked: Boolean = false
)

@Serializable
data class Shliste(
    val uuid: String,
    val name: String,
    val color: String,
    val secret: Boolean = false,
    val items: List<ListItem> = emptyList(),
    val lastEditied: Long = System.currentTimeMillis()
)
