package hn.single.server.features.main

import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.base.BaseActivity
import hn.single.server.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        //findNavController(R.id.mainSerContainerView)
    }

    override fun observeData() {

    }

    override fun setupActions() {

    }
}