package com.app.func.login_demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.func.coroutine_demo.retrofit.aaa.DataRepository
import com.app.func.view.recycler_view_custom.ravi_recyclerview.ItemCart
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpViewModel(private val repository: DataRepository) : ViewModel() {

    val errorMessage: LiveData<String> get() = repository.errorFood
    val menuFoodList: LiveData<Response<List<ItemCart>>> get() = repository.menuFood
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getListMenuFood() {
        viewModelScope.launch {
            _loading.postValue(true)
            repository.getMenuFood().apply {
                _loading.postValue(false)
            }
        }
    }
}