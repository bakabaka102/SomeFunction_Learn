package com.app.func.login_demo

import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentWaveDropBinding
import com.app.func.view.animation_view.WaveHelper

class WaveDropFragment : BaseFragment<FragmentWaveDropBinding>() {
    private var mWaveHelper: WaveHelper? = null
    override fun getViewBinding() = FragmentWaveDropBinding.inflate(layoutInflater)

    override fun setUpViews() {
        mWaveHelper = WaveHelper(binding.contentWater)
        binding.contentWater.setContent("120")

        binding.contentWater.onAnimationUp = {
            //startAnimationUp()
        }
        binding.contentWater.onAnimationDown = {
            //startAnimationDown()
        }
        mWaveHelper?.start()
    }

}