package com.app.func.startapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.func.MainActivityViewModel
import com.app.func.R
import com.app.func.ViewAnimationsActivity2
import com.app.func.ViewCustomActivity
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentHomeStartBinding
import com.app.func.view.all_demo.EmotionalFaceView
import com.app.func.view.chart.CHARTTYPE
import com.app.func.view.chart.StatisticsView
import com.app.func.view.chart.models.ReportResponse
import com.app.func.view.chart.models.Temperature
import com.app.func.view.chart.utils.ModelType
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeStartFragment : BaseFragment<FragmentHomeStartBinding>() {

    private var model = ModelType.FREEZER
    private val viewModel : MainActivityViewModel by viewModels()

    override fun getViewBinding(): FragmentHomeStartBinding {
        return FragmentHomeStartBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        showImage()
        loadChart()
        viewModel.downloadNote()
        viewModel.getNote()
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

    private fun loadChart() {
        val listData = ReportResponse(
            listOf(
                Temperature(10L, "123"),
                Temperature(13L, "98"),
                Temperature(20L, "116"),
                Temperature(16L, "87"),
                Temperature(2L, "78"),
                Temperature(8L, "80"),
                Temperature(1L, "100")
            ),
            listOf(
                Temperature(10L, "140"),
                Temperature(8L, "99"),
                Temperature(21L, "66"),
                Temperature(14L, "56"),
                Temperature(11L, "98"),
                Temperature(6L, "52"),
                Temperature(2L, "78"),
                Temperature(4L, "123"),
                Temperature(16L, "25")
            ),
        )
        binding?.customStaticView?.resetIndicatorView()
        loadStaticView(statisticsView = binding?.customStaticView, list = listData)


    }

    private fun loadStaticView(statisticsView: StatisticsView?, list: ReportResponse?) {
        if (list != null) {
            statisticsView?.loadData(
                list,
                chartType = if (model == ModelType.FREEZER || model == ModelType.FRIDGE || model == ModelType.HOT_WATER_TANK) CHARTTYPE.POWER else CHARTTYPE.WATER_OUT
            )
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