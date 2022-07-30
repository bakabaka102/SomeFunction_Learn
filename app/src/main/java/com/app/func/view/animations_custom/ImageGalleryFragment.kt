package com.app.func.view.animations_custom

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.ImageGalleryFragmentBinding
import kotlin.random.Random

class ImageGalleryFragment : BaseFragment() {

    private var binding: ImageGalleryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ImageGalleryFragmentBinding.inflate(inflater, container, false)
        //emitBubbles()
        initActions()
        return binding?.root
    }

    private fun emitBubbles() {
        Handler(Looper.getMainLooper()).postDelayed({
            val size = Random.nextInt(6, 16)
            //binding?.bubbleEmitterView?.emitBubble(size)
            emitBubbles()
        }, Random.nextLong(100, 500))
    }


    private fun initActions() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}