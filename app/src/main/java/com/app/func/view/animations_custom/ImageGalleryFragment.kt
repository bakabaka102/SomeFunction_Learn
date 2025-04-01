package com.app.func.view.animations_custom

import android.os.Handler
import android.os.Looper
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.ImageGalleryFragmentBinding
import kotlin.random.Random

class ImageGalleryFragment : BaseFragment<ImageGalleryFragmentBinding>() {

    private fun emitBubbles() {
        Handler(Looper.getMainLooper()).postDelayed({
            Random.nextInt(6, 16)
            emitBubbles()
        }, Random.nextLong(100, 500))
    }

    override fun getViewBinding() = ImageGalleryFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }


    override fun initActions() {
        emitBubbles()
    }
}