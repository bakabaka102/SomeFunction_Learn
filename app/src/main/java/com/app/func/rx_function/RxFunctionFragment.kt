package com.app.func.rx_function

import android.graphics.Point
import android.util.DisplayMetrics
import android.view.View
import android.widget.SeekBar
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentRxFunctionBinding
import kotlin.math.roundToInt

class RxFunctionFragment : BaseFragment<FragmentRxFunctionBinding>(), View.OnClickListener {

    private var dp16Pixel = 0

    private fun eventSeekbarChange() {
        binding.sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateMarker(binding.sb, progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.marker.rlMarker.isVisible = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.marker.rlMarker.isVisible = false
            }
        })
    }

    override fun getViewBinding() = FragmentRxFunctionBinding.inflate(layoutInflater)

    override fun setUpViews() {
        dp16Pixel = resources.getDimensionPixelOffset(R.dimen.dimen_16dp)
        eventSeekbarChange()
    }

    override fun initActions() {
        binding.apply {
            btnSimpleRx.setOnClickListener(this@RxFunctionFragment)
            btnMapRx.setOnClickListener(this@RxFunctionFragment)
            btnZipRx.setOnClickListener(this@RxFunctionFragment)
            btnTimeRx.setOnClickListener(this@RxFunctionFragment)
            btnFilterRx.setOnClickListener(this@RxFunctionFragment)
            btnConcatRx.setOnClickListener(this@RxFunctionFragment)
            btnMergeRx.setOnClickListener(this@RxFunctionFragment)
            btnDelayRx.setOnClickListener(this@RxFunctionFragment)
            btnSearchRx.setOnClickListener(this@RxFunctionFragment)
        }
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
                convertDpToPixel().roundToInt())
        val progress = " $message "
        binding.marker.tvProgress.text = progress
        binding.marker.tvProgress.post {
//            val display: Display =
//                (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val deviceDisplay = Point()
//            display.getSize(deviceDisplay)

            //vArrow always follow seekBar thumb location
            binding.marker.vArrow.x = (thumbPos - sb.thumbOffset).toFloat()

            //unlike vArrow, tvProgress will not always follow seekBar thumb location
            when {
                thumbPos - binding.marker.tvProgress.width / 2 - sb.paddingLeft < 0 -> {
                    //part of the tvProgress is to the left of 0 bound
                    binding.marker.tvProgress.x = binding.marker.vArrow.x - 20
                }

                thumbPos + binding.marker.tvProgress.width / 2 + sb.paddingRight > deviceDisplay.x -> {
                    //part of the tvProgress is to the right of screen width bound
                    binding.marker.tvProgress.x =
                        binding.marker.vArrow.x - binding.marker.tvProgress.width + 20 + binding.marker.vArrow.width
                }

                else -> {
                    //tvProgress is between 0 and screen width bounds
                    binding.marker.tvProgress.x = thumbPos - binding.marker.tvProgress.width / 2f
                }
            }
        }
    }

    private fun convertDpToPixel(dp: Float = 10f): Float {
        val metrics: DisplayMetrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onClick(view: View?) {
        when (view) {

            binding.btnSimpleRx -> {
                findNavController().navigate(R.id.simpleRXFragment)
            }

            binding.btnMapRx -> {
                findNavController().navigate(R.id.mapRXFragment)
            }

            binding.btnZipRx -> {
                findNavController().navigate(R.id.zipRXFragment)
            }

            binding.btnTimeRx -> {
                findNavController().navigate(R.id.timerRXFragment)
            }

            binding.btnFilterRx -> {
                findNavController().navigate(R.id.filterRXFragment)
            }

            binding.btnConcatRx -> {
                findNavController().navigate(R.id.concatRXFragment)
            }

            binding.btnMergeRx -> {
                findNavController().navigate(R.id.mergeRXFragment)
            }

            binding.btnDelayRx -> {
                findNavController().navigate(R.id.delayRXFragment)
            }

            binding.btnSearchRx -> {
                findNavController().navigate(R.id.searchByRXFragment)
            }
        }
    }
}