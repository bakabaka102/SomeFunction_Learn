package hn.single.server.ui.search

import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun isBottomNavVisible(): Boolean = false

}