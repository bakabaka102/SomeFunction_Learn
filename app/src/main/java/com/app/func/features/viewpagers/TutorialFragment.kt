package com.app.func.features.viewpagers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.view.isVisible
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentTutorialBinding
import com.app.func.features.viewpagers.model.Tutorial
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class TutorialFragment : BaseFragment<FragmentTutorialBinding>() {

    private val parentJob = Job()

    // 1
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            //2
            coroutineScope.launch(Dispatchers.Main) {
                //3
                binding.errorMessage.isVisible = true
                binding.errorMessage.text = getString(R.string.error_message)
            }

            coroutineScope.launch { println("Caught $throwable") }
        }

    private val coroutineScope =
        CoroutineScope(Dispatchers.Main + parentJob + coroutineExceptionHandler)

    override fun getViewBinding() = FragmentTutorialBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val tutorial = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(TUTORIAL_KEY, Tutorial::class.java)
        } else {
            arguments?.getParcelable(TUTORIAL_KEY)
        }
        coroutineScope.launch(Dispatchers.Main) {
            val originalBitmap: Bitmap? = tutorial?.let { getOriginalBitmapAsync(it) }
            //1
            //val snowFilterBitmap: Bitmap? = originalBitmap.let { loadSnowFilterAsync(it) }
            //2
            //snowFilterBitmap.let { loadImage(it) }
            originalBitmap?.let { loadImage(it) }
        }
        binding.tutorialName.text = tutorial?.name
        binding.tutorialDesc.text = tutorial?.description
    }

    override fun setTitleActionBar() {
        //Remove title
    }

    // 1
    private suspend fun getOriginalBitmapAsync(tutorial: Tutorial): Bitmap =
        withContext(Dispatchers.IO) {
            URL(tutorial.url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

    fun getBitmapFromInternet(imageURL: String): Bitmap? {
        val `in`: InputStream = URL(imageURL).openStream()
        return BitmapFactory.decodeStream(`in`)
    }

    private suspend fun loadSnowFilterAsync(originalBitmap: Bitmap): Bitmap =
        withContext(Dispatchers.Default) {
            SnowFilter.applySnowEffect(originalBitmap)
        }

    private fun loadImage(snowFilterBitmap: Bitmap) {
        binding.progressBar.isVisible = false
        binding.snowFilterImage.setImageBitmap(snowFilterBitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        parentJob.cancel()
    }

    companion object {
        const val TUTORIAL_KEY = "TUTORIAL"

        fun newInstance(tutorial: Tutorial): TutorialFragment {
            val args = Bundle().apply {
                putParcelable(TUTORIAL_KEY, tutorial)
            }
            val fragment = TutorialFragment().apply {
                arguments = args
            }
            return fragment
        }
    }
}