package hn.single.server.ui.randomvalue

import android.os.Parcelable
import hn.single.server.base.UiEffect
import hn.single.server.base.UiEvent
import hn.single.server.base.UiState
import kotlinx.parcelize.Parcelize

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

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val isFavorite: Boolean
) : Parcelable

@Parcelize
data class SettingsBundle(
    val data: Map<String, String> // hoặc Map<String, Serializable> nếu muốn đa kiểu
) : Parcelable