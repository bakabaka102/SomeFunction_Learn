package com.app.func.thread_demo.snowy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.func.R
import com.app.func.databinding.FragmentTutorialBinding
import com.app.func.thread_demo.snowy.model.Tutorial
import com.app.func.thread_demo.snowy.utils.SnowFilter
import kotlinx.coroutines.*
import java.net.URL

class TutorialFragment : Fragment() {

    companion object {

        const val TUTORIAL_KEY = "TUTORIAL"
        private var binding: FragmentTutorialBinding? = null

        fun newInstance(tutorial: Tutorial): TutorialFragment {
            val fragmentHome = TutorialFragment()
            val args = Bundle()
            args.putParcelable(TUTORIAL_KEY, tutorial)
            fragmentHome.arguments = args
            return fragmentHome
        }
    }

    //
    private val parentJob = Job()

    // 1
    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            //2
            coroutineScope.launch(Dispatchers.Main) {
                //3
                binding?.errorMessage?.visibility = View.VISIBLE
                binding?.errorMessage?.text = getString(R.string.error_message)
            }

            GlobalScope.launch { println("Caught $throwable") }
        }

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob +
                coroutineExceptionHandler
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val tutorial = arguments?.getParcelable(TUTORIAL_KEY) as? Tutorial
//        val view = inflater.inflate(R.layout.fragment_tutorial, container, false)
        binding = FragmentTutorialBinding.inflate(inflater, container, false)
        binding?.tutorialName?.text = tutorial?.name
        binding?.tutorialDesc?.text = tutorial?.description
        return binding?.root
    }

    //
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tutorial = arguments?.getParcelable(TUTORIAL_KEY) as? Tutorial

        coroutineScope.launch(Dispatchers.Main) {
            val originalBitmap: Bitmap? = tutorial?.let { getOriginalBitmapAsync(it) }
            //1
            val snowFilterBitmap: Bitmap? = originalBitmap?.let { loadSnowFilterAsync(it) }
            //2
            snowFilterBitmap?.let { loadImage(it) }
        }
    }

    // 1
    private suspend fun getOriginalBitmapAsync(tutorial: Tutorial): Bitmap =
        withContext(Dispatchers.IO) {
            URL(tutorial.url).openStream().use {
                return@withContext BitmapFactory.decodeStream(it)
            }
        }

    private suspend fun loadSnowFilterAsync(originalBitmap: Bitmap): Bitmap =
        withContext(Dispatchers.Default) {
            SnowFilter.applySnowEffect(originalBitmap)
        }

    private fun loadImage(snowFilterBitmap: Bitmap) {
        binding?.progressBar?.visibility = View.GONE
        binding?.snowFilterImage?.setImageBitmap(snowFilterBitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        parentJob.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}