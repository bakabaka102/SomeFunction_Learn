package com.app.func.rx_function

import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentRxFunctionBinding
import kotlin.math.roundToInt

class RxFunctionFragment : BaseFragment<FragmentRxFunctionBinding>(), View.OnClickListener {

    private var dp16Pixel = 0

    private val mBinding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRxFunctionBinding.inflate(inflater)
        dp16Pixel = resources.getDimensionPixelOffset(R.dimen.dimen_16dp)
        eventSeekbarChange()
        initActions()
        return binding?.root
    }

    private fun eventSeekbarChange() {
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
    }

    override fun getViewBinding() = FragmentRxFunctionBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnSimpleRx?.setOnClickListener(this)
        binding?.btnMapRx?.setOnClickListener(this)
        binding?.btnZipRx?.setOnClickListener(this)
        binding?.btnTimeRx?.setOnClickListener(this)
        binding?.btnFilterRx?.setOnClickListener(this)
        binding?.btnConcatRx?.setOnClickListener(this)
        binding?.btnMergeRx?.setOnClickListener(this)
        binding?.btnDelayRx?.setOnClickListener(this)
        binding?.btnSearchRx?.setOnClickListener(this)
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
        val progress = " $message "
        mBinding.marker.tvProgress.text = progress
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
        val metrics: DisplayMetrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onClick(view: View?) {
        when (view) {

            binding?.btnSimpleRx -> {
                findNavController().navigate(R.id.simpleRXFragment)
            }

            binding?.btnMapRx -> {
                findNavController().navigate(R.id.mapRXFragment)
            }

            binding?.btnZipRx -> {
                findNavController().navigate(R.id.zipRXFragment)
            }

            binding?.btnTimeRx -> {
                findNavController().navigate(R.id.timerRXFragment)
            }

            binding?.btnFilterRx -> {
                findNavController().navigate(R.id.filterRXFragment)
            }

            binding?.btnConcatRx -> {
                findNavController().navigate(R.id.concatRXFragment)
            }

            binding?.btnMergeRx -> {
                findNavController().navigate(R.id.mergeRXFragment)
            }

            binding?.btnDelayRx -> {
                findNavController().navigate(R.id.delayRXFragment)
            }

            binding?.btnSearchRx -> {
                findNavController().navigate(R.id.searchByRXFragment)
            }
        }
    }
}