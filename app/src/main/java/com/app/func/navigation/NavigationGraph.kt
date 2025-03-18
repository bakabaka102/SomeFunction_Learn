package com.app.func.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

enum class Screen {
    MAIN_ANIMATION,
    BUBBLE_ANIMATION,
    BUBBLE_EMITTER_ANIMATION
}

@Serializable
object MainAnimation

@Serializable
object BubbleAnimation

@Serializable
object BubbleEmitterAnimation

@Serializable
data class Main(val id: String)

@Serializable
data class Bubble(val id: String)

@Serializable
data class Emitter(val id: String)