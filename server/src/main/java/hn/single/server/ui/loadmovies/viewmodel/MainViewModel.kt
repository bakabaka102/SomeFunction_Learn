package hn.single.server.ui.loadmovies.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hn.single.server.common.UIState
import hn.single.server.data.model.MainData
import hn.single.server.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    private val _mainItem = MutableStateFlow<UIState<List<MainData>>>(UIState.Loading)
    val mainItem: MutableStateFlow<UIState<List<MainData>>> get() = _mainItem

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _mainItem.emit(UIState.Loading)
            mainRepository.getMainData()/*.flowOn(Dispatchers.IO)*/.catch {
                Log.d("TAG","Error - $it")
                _mainItem.emit(UIState.Failure(it))
            }.collect {
                _mainItem.emit(UIState.Success(it))
            }
        }
    }

}