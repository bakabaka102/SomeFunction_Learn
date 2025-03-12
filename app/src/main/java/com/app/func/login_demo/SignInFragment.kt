package com.app.func.login_demo

import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSignInBinding
import com.app.func.view.animation_view.WaveHelper

class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private var mWaveHelper: WaveHelper? = null
    override fun getViewBinding() = FragmentSignInBinding.inflate(layoutInflater)

    override fun setUpViews() {
        mWaveHelper = WaveHelper(binding?.contentWater)
        binding?.contentWater?.setContent("120")

        binding?.contentWater?.onAnimationUp = {
            //startAnimationUp()
        }
        binding?.contentWater?.onAnimationDown = {
            //startAnimationDown()
        }
        mWaveHelper?.start()
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }
}