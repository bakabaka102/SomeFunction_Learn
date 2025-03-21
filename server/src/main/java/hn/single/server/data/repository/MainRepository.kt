package hn.single.server.data.repository

import hn.single.server.data.model.MainData
import hn.single.server.data.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface,
) {
    fun getMainData(): Flow<List<MainData>> {
        return flow {
            emit(apiInterface.getMoviesData().dataList ?: emptyList())
        }
    }
}