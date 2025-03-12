package com.app.func.view.animations_custom

import android.os.Handler
import android.os.Looper
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.BubbleEmitterFragmentBinding
import kotlin.random.Random

class BubbleEmitterFragment : BaseFragment<BubbleEmitterFragmentBinding>() {


    private fun emitBubbles() {
        Handler(Looper.getMainLooper()).postDelayed({
            val size = Random.nextInt(6, 16)
            binding?.bubbleEmitterView?.emitBubble(size)
            emitBubbles()
        }, Random.nextLong(100, 500))
    }

    override fun getViewBinding() = BubbleEmitterFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {
        emitBubbles()
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

}