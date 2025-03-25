package com.app.func.startapp

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.net.toUri
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

class HomeStartFragment : BaseFragment<FragmentHomeStartBinding>() {

    private var model = ModelType.FREEZER
    private val viewModel: MainActivityViewModel by viewModels()

    override fun getViewBinding() = FragmentHomeStartBinding.inflate(layoutInflater)

    override fun setUpViews() {
        showImage()
        loadChart()
        viewModel.downloadNote()
        viewModel.getNote()
        loadJson<String>(this@HomeStartFragment.context, fileJson)
        /*val uri = Uri.parse("${CUSTOM_PROVIDER_URI}/${fileJson}")
        val resolver = context?.contentResolver
        val inputStream = resolver?.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        while ((reader.readLine().also { line = it }) != null) {
            Log.d(Constants.TAG_PROVIDER, "Content - $line")
        }
        reader.close()*/
    }

    private val fileJson = "data_custom.json"
    private val CUSTOM_PROVIDER_URI get() = "content://hn.single.server.provider.AssetsFileProvider"


    private inline fun <reified T> loadJson(context: Context?, fileName: String): T? {
        context?.packageManager?.getResourcesForApplication("hn.single.server")?.let {
            val inputStream = it.assets.open(fileName)
            val content = inputStream.bufferedReader().use(BufferedReader::readText)
            Log.d(Constants.TAG_PROVIDER, "Content of file = $content")
            return content as T
        }
        return null
        /*val contentResolver = context?.contentResolver
        contentResolver?.readCustomFile(CUSTOM_PROVIDER_URI, fileName)?.let {
            Log.d(Constants.TAG_PROVIDER, "Content of file = $it")
            return it as T
        }
        return null*/
        //throw InvalidParameterException("Fail to Load $fileName File")
    }

    private fun ContentResolver.readCustomFile(
        customProviderUri: String,
        file: String,
        mandatory: Boolean = false
    ): String? {
        return try {
            val assetUri = "$customProviderUri/$file".toUri().also {
                Log.d(Constants.TAG_PROVIDER, "Asset Uri = $it")
            }
            val inputStream = openInputStream(assetUri)
            inputStream?.let {
                val content = it.bufferedReader().use(BufferedReader::readText)
                // Process the content
                it.close()
                content
            }
        } catch (ex: IOException) {
            if (mandatory) {
                Log.d(Constants.TAG_PROVIDER, "File Not Found = $file", ex)
            }
            null
        }
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
            startActivity(Intent(requireActivity(), ViewCustomActivity::class.java))
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
            } catch (e: MalformedURLException) {
                Log.d("aaa - e", e.message.toString())
            } catch (eio: IOException) {
                Log.d("aaa - eio", eio.message.toString())
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