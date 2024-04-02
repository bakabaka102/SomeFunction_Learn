package com.app.func.startapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.app.func.R
import com.app.func.ViewAnimationsActivity2
import com.app.func.ViewCustomActivity
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentHomeStartBinding
import com.app.func.view.all_demo.EmotionalFaceView
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeStartFragment : BaseFragment<FragmentHomeStartBinding>() {

    override fun getViewBinding(): FragmentHomeStartBinding {
        return FragmentHomeStartBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        showImage()
    }

    override fun observeData() {

    }

    override fun observeView() {

    }

    override fun initActions() {
        binding?.btnRX?.setOnClickListener {
            findNavController().navigate(R.id.rxFunctionFragment)
        }

        binding?.btnCoroutines?.setOnClickListener {
            findNavController().navigate(R.id.coroutinesFragment)
        }
        binding?.happyButton?.setOnClickListener {
            binding?.emotionalFaceView?.happinessState = EmotionalFaceView.HAPPY
        }

        binding?.sadButton?.setOnClickListener {
            binding?.emotionalFaceView?.happinessState = EmotionalFaceView.SAD
        }

        binding?.btnThread?.setOnClickListener {
            findNavController().navigate(R.id.mainContainFragment)
        }
        binding?.btnViewCustom?.setOnClickListener {
            startActivity(Intent(requireActivity(), ViewCustomActivity::class.java))
        }
        binding?.btnParseJson?.setOnClickListener {
            findNavController().navigate(R.id.jsonFuncFragment)
        }
        binding?.btnAnimation?.setOnClickListener {
            startActivity(Intent(requireContext(), ViewAnimationsActivity2::class.java))
        }

        binding?.btnDataBinding?.setOnClickListener {

        }
    }

    private fun showImage() {
        val imageJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val imagePath =
                    "https://img.freepik.com/free-vector/3d-cartoon-character-woman-working-with-laptop-search-bar-illustration-vector-design_40876-3096.jpg"
                val imageBitmap = loadImage(imagePath)
                withContext(Dispatchers.Main) {
                    binding?.imageView?.setImageBitmap(imageBitmap)
                }
            } catch (e: MalformedURLException) {
                Log.d("aaa - e", e.message.toString())
            } catch (eio: IOException) {
                Log.d("aaa - eio", eio.message.toString())
            }
        }
        //imageJob.cancel()
    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun loadImage(imagePath: String): Bitmap {
        val url = URL(imagePath)
        val inputStream = url.openConnection().getInputStream()
        return BitmapFactory.decodeStream(inputStream)
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeStartFragment()

    }
}