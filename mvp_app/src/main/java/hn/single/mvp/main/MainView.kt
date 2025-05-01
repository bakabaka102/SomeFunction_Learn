package hn.single.mvp.main

import hn.single.mvp.base.BaseView

// MainView.kt
interface MainView : BaseView {
    fun showData(data: String)
}
