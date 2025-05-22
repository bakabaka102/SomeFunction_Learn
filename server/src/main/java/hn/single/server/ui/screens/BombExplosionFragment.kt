package hn.single.server.ui.screens

import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentBombExplosionBinding

class BombExplosionFragment : BaseFragment<FragmentBombExplosionBinding>() {

    override fun getViewBinding() = FragmentBombExplosionBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun isBottomNavVisible(): Boolean = false
}