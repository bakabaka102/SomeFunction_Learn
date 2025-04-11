package com.app.func.startapp

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.func.MainActivityViewModel
import com.app.func.R
import com.app.func.ViewAnimationsActivity2
import com.app.func.ViewCustomActivity
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentHomeStartBinding
import com.app.func.utils.Constants
import com.app.func.view.all_demo.EmotionalFaceView
import com.app.func.view.chart.ChartType
import com.app.func.view.chart.StatisticsView
import com.app.func.view.chart.models.ReportResponse
import com.app.func.view.chart.models.reportResponse
import com.app.func.view.chart.utils.ModelType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import kotlin.text.compareTo

class HomeStartFragment : BaseFragment<FragmentHomeStartBinding>() {

    private var model = ModelType.FREEZER
    private val viewModel: MainActivityViewModel by viewModels()
    private val fileJson = "data_custom.json"

    override fun getViewBinding() = FragmentHomeStartBinding.inflate(layoutInflater)

    override fun setUpViews() {
        showImage()
        loadChart()
        viewModel.downloadNote()
        viewModel.getNote()
        loadJsonFromAssetsOtherApp<String>(this@HomeStartFragment.context, fileJson)
    }

    private inline fun <reified T> loadJsonFromAssetsOtherApp(context: Context?, fileName: String): T? {
        val packageNameResourceApp = "hn.single.server"
        context?.packageManager?.getResourcesForApplication(packageNameResourceApp)?.let {
            val inputStream = it.assets.open(fileName)
            val content = inputStream.bufferedReader().use(BufferedReader::readText)
            Log.d(Constants.TAG_PROVIDER, "Content of file = $content")
            return content as T
        }
        return null
    }

    override fun observeData() {
        viewModel.note.observe(viewLifecycleOwner) {
            Log.d("fileTag", it.toString())
        }

        viewModel.response.observe(viewLifecycleOwner) {
            Log.d("fileTag", "Response $it")
            viewModel.saveFileToDisk(requireContext(), responseBody = it, fileName = "test.json")
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.d("fileTag", it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            Log.d("fileTag", "isLoading --- $it")
        }

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
            val intent = Intent(requireActivity(), ViewCustomActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                activity,
                R.anim.slide_in_right,
                R.anim.slide_out_left,
            )
            startActivity(intent, options.toBundle())
        }
        binding?.btnParseJson?.setOnClickListener {

        }
        binding?.btnAnimation?.setOnClickListener {
            startActivity(Intent(requireContext(), ViewAnimationsActivity2::class.java))
        }

        binding?.btnRetrofit?.setOnClickListener {
            findNavController().navigate(R.id.retrofitFragment)
        }
    }

    private fun loadChart() {
        val listData: ReportResponse = reportResponse()
        binding?.customStaticView?.resetIndicatorView()
        loadStaticView(statisticsView = binding?.customStaticView, list = listData)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadStaticView(statisticsView: StatisticsView?, list: ReportResponse?) {
        if (list != null) {
            statisticsView?.loadData(
                list,
                chartType = if (model == ModelType.FREEZER || model == ModelType.FRIDGE || model == ModelType.HOT_WATER_TANK) ChartType.POWER else ChartType.WATER_OUT
            )
        }
    }

    private fun showImage() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imagePath =
                    "https://img.freepik.com/free-vector/3d-cartoon-character-woman-working-with-laptop-search-bar-illustration-vector-design_40876-3096.jpg"
                val imageBitmap = loadImage(imagePath)
                withContext(Dispatchers.Main) {
                    binding?.imageView?.setImageBitmap(imageBitmap)
                }
            } catch (e: Exception) {
                when (e) {
                    is MalformedURLException -> Log.d(
                        "Exception",
                        "MalformedURLException xảy ra: ${e.message}"
                    )

                    is IOException -> Log.d("Exception", "IOException - ${e.message}")
                    else -> Log.d("Exception", "Other exception - ${e.message}")
                }
            }
        }
    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun loadImage(imagePath: String): Bitmap {
        val url = URL(imagePath)
        val inputStream = url.openConnection().getInputStream()
        return BitmapFactory.decodeStream(inputStream)
    }
}