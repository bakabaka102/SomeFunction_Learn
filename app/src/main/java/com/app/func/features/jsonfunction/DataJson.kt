package com.app.func.features.jsonfunction

import kotlinx.serialization.Serializable

@Serializable
data class DataJson(
    val home: List<TabInfo>,
    val content: List<TabInfo>,
    val about: List<TabInfo>,
)

@Serializable
data class TabInfo(
    val default: String,
    val title: String,
    val type: String
)

enum class DataTabJson {
    HOME, CONTENT, ABOUT
}