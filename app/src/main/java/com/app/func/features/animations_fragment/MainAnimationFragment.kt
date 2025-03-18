package com.app.func.features.animations_fragment

import androidx.navigation.fragment.findNavController
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.MainAnimationFragmentBinding
import com.app.func.navigation.Screen

class MainAnimationFragment : BaseFragment<MainAnimationFragmentBinding>() {
    override fun getViewBinding() = MainAnimationFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {


    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnBall1?.setOnClickListener {
            findNavController().navigate(Screen.BUBBLE_ANIMATION.name)
        }
        binding?.btnBall2?.setOnClickListener {
            findNavController().navigate(Screen.BUBBLE_EMITTER_ANIMATION.name)
        }
    }


}