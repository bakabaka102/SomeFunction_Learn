package hn.single.server.ui.randomvalue

import hn.single.server.base.UiEffect
import hn.single.server.base.UiEvent
import hn.single.server.base.UiState

class RandomContract {

    sealed class Event : UiEvent {
        data object OnRandomNumberClicked : Event()
        data object OnShowToastClicked : Event()
    }

    data class State(
        val randomNumberState: RandomNumberState
    ) : UiState

    sealed class RandomNumberState {
        data object Idle : RandomNumberState()
        data object Loading : RandomNumberState()
        data class Success(val number : Int) : RandomNumberState()
    }

    sealed class Effect : UiEffect {

        data object ShowToast : Effect()

    }

}