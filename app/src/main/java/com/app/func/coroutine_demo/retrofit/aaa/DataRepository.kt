package com.app.func.coroutine_demo.retrofit.aaa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.func.networks.IQuotableService
import com.app.func.view.recycler_view_custom.ravi_recyclerview.ItemCart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DataRepository(private val service: IQuotableService) : IDataRepository{

    private val _errorMovie = MutableLiveData<String>()
    val errorMovie : LiveData<String> get() = _errorMovie

    private val _errorFood = MutableLiveData<String>()
    val errorFood: LiveData<String> get() = _errorFood

    private val _allMovies = MutableLiveData<Response<List<Movie>>>()
    val allMovies : LiveData<Response<List<Movie>>>
        get() = _allMovies

    private val _menuFood = MutableLiveData<Response<List<ItemCart>>>()
    val menuFood: LiveData<Response<List<ItemCart>>> get() = _menuFood

    override suspend fun getAllMovies() {
        withContext(Dispatchers.IO){
            try {
                val response = service.getAllMovies()
                if (response.isSuccessful) {
                    _allMovies.postValue(response)
                }
            } catch (ex: Exception) {
                _errorMovie.postValue(ex.message)
            }
        }
    }

    override suspend fun getMenuFood() {
        withContext(Dispatchers.IO) {
            try {
                val response = service.getMenuFood()
                if (response.isSuccessful) {
                    _menuFood.postValue(response)
                }
            } catch (ex: Exception) {
                _errorFood.postValue(ex.message)
            }

        }
    }
}

interface IDataRepository {

    suspend fun getAllMovies()

    suspend fun getMenuFood()
}