package hn.single.mvp.base

// BaseView.kt
interface BaseView {

    fun showLoading()

    fun hideLoading()

    fun showError(message: String)
}
