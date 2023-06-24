package com.app.func.features.animations_fragment

import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.MainAnimationFragmentBinding

class MainAnimationFragment : BaseFragment<MainAnimationFragmentBinding>() {
    override fun getViewBinding(): MainAnimationFragmentBinding {
        return MainAnimationFragmentBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnBall1?.setOnClickListener {
            getNavController()?.navigate(R.id.bubbleAnimationFragment)
        }
        binding?.btnBall2?.setOnClickListener {
            getNavController()?.navigate(R.id.bubbleEmitterFragment)
        }
    }
}