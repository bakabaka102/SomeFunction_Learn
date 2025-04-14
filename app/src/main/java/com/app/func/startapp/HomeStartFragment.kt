package com.app.func.startapp

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.func.MainActivityViewModel
import com.app.func.R
import com.app.func.ViewAnimationsActivity2
import com.app.func.ViewCustomActivity
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentHomeStartBinding
import com.app.func.provider.SharedImageManager
import com.app.func.utils.Constants
import com.app.func.utils.Constants.PKG_RESOURCE_APP
import com.app.func.utils.Logger
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
    private val fileJson = "data_custom.json"
    private val providerURI = "hn.single.server.sharedprovider"

    private val imageList = mutableListOf<Bitmap>()

    override fun getViewBinding() = FragmentHomeStartBinding.inflate(layoutInflater)

    override fun setUpViews() {
        showImage()
        loadChart()
        viewModel.downloadNote()
        viewModel.getNote()
        loadJsonFromAssetsOtherApp<String>(this@HomeStartFragment.context, fileJson)

        val bitmap = SharedImageManager.getImage(requireContext(), "water_blue")
        binding?.imageView?.setImageBitmap(bitmap)

        //fetchSharedStrings()
        //fetchImageList()
        //loadImageByGetIdentifier()
    }

    private fun loadImageByGetIdentifier() {
        val resourceAppContext =
            context?.createPackageContext(PKG_RESOURCE_APP, Context.CONTEXT_IGNORE_SECURITY)
        val resId = resourceAppContext?.resources?.getIdentifier(
            "water_gray",
            "drawable",
            PKG_RESOURCE_APP
        ) as Int
        if (resId != 0) {
            //val drawable = resourceAppContext?.resources?.getDrawable(resId, null)
            val drawable = ResourcesCompat.getDrawable(resourceAppContext.resources, resId, null)
            showImageDemo(drawable = drawable)
        } else {
            Logger.e("Resource not found.")
        }
    }

    private fun showImageDemo(bitmap: Bitmap? = null, drawable: Drawable? = null) {
        binding?.imageView?.apply {
            bitmap?.let {
                setImageBitmap(it)
            }
            drawable?.let {
                setImageDrawable(it)
            }
        }
    }

    private fun fetchSharedStrings() {
        val uri = "content://$providerURI/strings".toUri()
        val cursor = context?.contentResolver?.query(uri, null, null, null, null)

        cursor?.use {
            val keyIndex = it.getColumnIndex("key")
            val valueIndex = it.getColumnIndex("value")

            while (it.moveToNext()) {
                val key = it.getString(keyIndex)
                val value = it.getString(valueIndex)
                Logger.d("Client --- KEY: $key | VALUE: $value")
            }
        }
    }

    private fun fetchImageList() {
        val uri = "content://$providerURI/image_list".toUri()
        val cursor = context?.contentResolver?.query(uri, null, null, null, null)

        cursor?.use {
            val nameIndex = it.getColumnIndex("name")
            val uriIndex = it.getColumnIndex("uri")

            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                val imageUri = it.getString(uriIndex).toUri().also {
                    Logger.d("Client - Image: $name - $it")
                }
                try {
                    context?.contentResolver?.openFileDescriptor(imageUri, "r")?.use { pfd ->
                        val bitmap = BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                        if (bitmap != null) {
                            bitmap.apply {
                                imageList.add(this)
                                //showImageDemo(bitmap = this)
                            }
                            Logger.d("✅ Client - Decode OK for: $imageUri")
                        } else {
                            Logger.w("❌ Client - Decode failed (bitmap=null) for: $imageUri")
                        }
                    }
                } catch (e: Exception) {
                    Logger.e("❌ Client - Exception decoding image: ${e.message}")
                }
            }
        }
    }

    private inline fun <reified T> loadJsonFromAssetsOtherApp(
        context: Context?,
        fileName: String
    ): T? {
        context?.packageManager?.getResourcesForApplication(PKG_RESOURCE_APP)?.let {
            val inputStream = it.assets.open(fileName)
            val content = inputStream.bufferedReader().use(BufferedReader::readText)
            Logger.d("Content of file = $content")
            return content as T
        }
        return null
    }

    override fun observeData() {
        viewModel.note.observe(viewLifecycleOwner) {
            Logger.d("fileTag - $it")
        }

        viewModel.response.observe(viewLifecycleOwner) {
            Logger.d("Response $it")
            viewModel.saveFileToDisk(requireContext(), responseBody = it, fileName = "test.json")
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Logger.d("Error - $it")
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d("isLoading --- $it")
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
                val imageBitmap = loadImage()
                withContext(Dispatchers.Main) {
                    //binding?.imageView?.setImageBitmap(imageBitmap)
                }
            } catch (e: Exception) {
                when (e) {
                    is MalformedURLException -> Logger.d("MalformedURLException xảy ra: ${e.message}")

                    is IOException -> Logger.d("IOException - ${e.message}")
                    else -> Logger.d("Other exception - ${e.message}")
                }
            }
        }
    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun loadImage(imagePath: String = IMAGE_PATH): Bitmap {
        val url = URL(imagePath)
        val inputStream = url.openConnection().getInputStream()
        return BitmapFactory.decodeStream(inputStream)
    }

    companion object {
        private const val IMAGE_PATH =
            "https://img.freepik.com/free-vector/3d-cartoon-character-woman-working-with-laptop-search-bar-illustration-vector-design_40876-3096.jpg"
    }
}