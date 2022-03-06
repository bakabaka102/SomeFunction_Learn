package com.app.func.coroutine_demo

import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentCoroutinesBinding
import kotlin.math.roundToInt

class CoroutinesFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentCoroutinesBinding? = null

    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoroutinesBinding.inflate(inflater)

        binding?.sb?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateMarker(mBinding.sb, progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding?.marker?.rlMarker?.visibility = View.VISIBLE
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding?.marker?.rlMarker?.visibility = View.GONE
            }
        })
//        return inflater.inflate(R.layout.fragment_coroutines, container, false)
        initActions()
        return binding?.root
    }

    private fun initActions() {
        binding?.btnForecast?.setOnClickListener(this)
        binding?.btnSimpleRx?.setOnClickListener(this)
    }

    private fun updateMarker(sb: SeekBar, message: String) {
        /**
         * According to this question:
         * https://stackoverflow.com/questions/20493577/android-seekbar-thumb-position-in-pixel
         * one can find the SeekBar thumb location in pixels using:
         */
        val width = (sb.width - sb.paddingLeft - sb.paddingRight)
        val thumbPos = (sb.paddingLeft + (width * sb.progress / sb.max) +
                //take into consideration the margin added (in this case it is 10dp)
                convertDpToPixel(10f).roundToInt())
        mBinding.marker.tvProgress.text = " $message "
        mBinding.marker.tvProgress.post {
//            val display: Display =
//                (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val deviceDisplay = Point()
//            display.getSize(deviceDisplay)

            //vArrow always follow seekBar thumb location
            mBinding.marker.vArrow.x = (thumbPos - sb.thumbOffset).toFloat()

            //unlike vArrow, tvProgress will not always follow seekBar thumb location
            when {
                thumbPos - mBinding.marker.tvProgress.width / 2 - sb.paddingLeft < 0 -> {
                    //part of the tvProgress is to the left of 0 bound
                    mBinding.marker.tvProgress.x = mBinding.marker.vArrow.x - 20
                }
                thumbPos + mBinding.marker.tvProgress.width / 2 + sb.paddingRight > deviceDisplay.x -> {
                    //part of the tvProgress is to the right of screen width bound
                    mBinding.marker.tvProgress.x =
                        mBinding.marker.vArrow.x - mBinding.marker.tvProgress.width + 20 + mBinding.marker.vArrow.width
                }
                else -> {
                    //tvProgress is between 0 and screen width bounds
                    mBinding.marker.tvProgress.x = thumbPos - mBinding.marker.tvProgress.width / 2f
                }
            }
        }
    }


    private fun convertDpToPixel(dp: Float): Float {
        val resources: Resources = resources
        val metrics: DisplayMetrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnForecast -> {
                getNavController()?.navigate(R.id.weatherDemoFragment)
            }
            binding?.btnSimpleRx -> {
                getNavController()?.navigate(R.id.simpleRXFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}