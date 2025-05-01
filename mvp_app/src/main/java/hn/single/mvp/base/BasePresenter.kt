package hn.single.mvp.base

// BasePresenter.kt
interface BasePresenter<T : BaseView> {

    fun attach(view: T)

    fun detach()
}
