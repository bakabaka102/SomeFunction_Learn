package hn.single.mvp.main

import hn.single.mvp.base.BasePresenter

// MainPresenter.kt
interface MainPresenter : BasePresenter<MainView> {
    fun loadData()
}
