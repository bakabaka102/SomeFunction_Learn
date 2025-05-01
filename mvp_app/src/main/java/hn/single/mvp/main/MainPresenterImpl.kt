package hn.single.mvp.main

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPresenterImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MainPresenter {

    private var view: MainView? = null
    private var job: Job? = null

    override fun loadData() {
        view?.showLoading()
        job = CoroutineScope(dispatcher).launch {
            delay(2000) // giả lập gọi API

            val success = true // hoặc random để test lỗi
            withContext(Dispatchers.Main) {
                view?.hideLoading()
                if (success) {
                    view?.showData("Data loaded successfully!")
                } else {
                    view?.showError("Failed to load data.")
                }
            }
        }
    }

    override fun attach(view: MainView) {
        this.view = view
        // Initialize any resources or listeners needed for the view
    }

    override fun detach() {
        job?.cancel() //Cancel Coroutine when detaching
        view = null
    }

}