package com.app.func.login_demo.tab_viewpager

import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentTabViewpagerBinding

class TabFirstFragment : BaseFragment<FragmentTabViewpagerBinding>() {
    override fun getViewBinding() = FragmentTabViewpagerBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    companion object {
        fun newFragment() = TabFirstFragment()
    }
}