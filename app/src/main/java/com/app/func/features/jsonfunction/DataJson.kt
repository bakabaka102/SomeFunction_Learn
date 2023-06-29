package com.app.func.features.jsonfunction

import kotlinx.serialization.Serializable

@Serializable
data class DataJson(
    val home: List<Home>,
    val content: List<Content>,
    val about: List<About>,
)

@Serializable
data class About(
    val default: String,
    val title: String,
    val type: String
)

@Serializable
data class Content(
    val default: String,
    val title: String,
    val type: String
)

@Serializable
data class Home(
    val default: String,
    val title: String,
    val type: String
)

enum class DataTabJson {
    HOME, CONTENT, ABOUT
}