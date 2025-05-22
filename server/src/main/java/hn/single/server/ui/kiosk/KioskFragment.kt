package hn.single.server.ui.kiosk

import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentKioskBinding

class KioskFragment : BaseFragment<FragmentKioskBinding>() {

    override fun getViewBinding() = FragmentKioskBinding.inflate(layoutInflater)

    override fun getToolbarTitle() = "Kiosk"

    override fun isToolbarBackVisible() = false

    override fun setUpViews() {
        //setupToolbar("Kiosk")
    }

    override fun isBottomNavVisible(): Boolean = true

}