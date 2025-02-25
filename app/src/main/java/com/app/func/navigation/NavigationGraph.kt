package com.app.func.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

enum class Screen (value: String) {
    MAIN_ANIMATION("MainAnimation"),
    BUBBLE_ANIMATION("BubbleAnimation"),
    BUBBER_EMITTER_ANIMATION("BubbleEmitterAnimation")
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

object NavGraph {
    const val Id = 1

    object Screen {
        const val List = 2
        const val Display = 3
    }

    object Action {
        object DisplayModel {
            const val Id = 4

            fun NavController.displayModel(modelId: String) {
                navigate(Id, bundleOf(Args.MODEL_ID to modelId))
            }
        }
    }

    object Args {
        const val MODEL_ID = "ModelId"
    }
}