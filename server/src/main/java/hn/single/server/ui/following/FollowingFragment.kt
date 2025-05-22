package hn.single.server.ui.following

import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentFollowingBinding

class FollowingFragment : BaseFragment<FragmentFollowingBinding>() {

    override fun getViewBinding() = FragmentFollowingBinding.inflate(layoutInflater)

    override fun getToolbarTitle(): String? = "Following"

    override fun isToolbarBackVisible(): Boolean = false

    override fun setUpViews() {

    }

    override fun isBottomNavVisible(): Boolean = true
}