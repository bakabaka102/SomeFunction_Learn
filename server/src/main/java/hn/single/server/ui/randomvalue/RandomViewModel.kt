package hn.single.server.ui.randomvalue

import androidx.lifecycle.viewModelScope
import hn.single.server.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RandomViewModel : BaseViewModel<RandomContract.Event, RandomContract.State, RandomContract.Effect>() {

    override fun handleEvent(event: RandomContract.Event) {
        when (event) {
            is RandomContract.Event.OnRandomNumberClicked -> generateRandomNumber()
            is RandomContract.Event.OnShowToastClicked -> setEffect { RandomContract.Effect.ShowToast }
        }
    }

    override fun createInitialState(): RandomContract.State {
        return RandomContract.State(randomNumberState = RandomContract.RandomNumberState.Idle)
    }

    private fun generateRandomNumber() {
        //awaitClose
        viewModelScope.launch {
            // Set Loading
            setState { copy(randomNumberState = RandomContract.RandomNumberState.Loading) }
            try {
                delay(500)
                val random = (0..20).random()
                if (random % 2 == 0) {
                    setState { copy(randomNumberState = RandomContract.RandomNumberState.Idle) }
                    throw RuntimeException("Number is even")
                }
                setState { copy(randomNumberState = RandomContract.RandomNumberState.Success(number = random)) }
            } catch (exception: Exception) {
                setEffect {
                    RandomContract.Effect.ShowToast
                }
            }
        }
    }
}
