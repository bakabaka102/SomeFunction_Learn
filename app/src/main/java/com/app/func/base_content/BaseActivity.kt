package com.app.func.base_content

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var mBinding: VB

    abstract fun instanceViewBinding(): VB

    abstract fun initViews()

    open fun initActions() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = instanceViewBinding()
        setContentView(mBinding.root)
        initViews()
        initActions()
    }
}