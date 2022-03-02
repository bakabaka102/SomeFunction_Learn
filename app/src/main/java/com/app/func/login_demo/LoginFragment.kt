package com.app.func.login_demo

import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.app.func.R
import com.app.func.databinding.LoginFragmentBinding
import kotlin.math.roundToInt

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    private fun updateMarker(sb: SeekBar, rlMarker: View, message: String) {
        val tvProgress = rlMarker.findViewById(R.id.tvProgress) as TextView
        val vArrow: View = rlMarker.findViewById(R.id.vArrow) as View

        /**
         * According to this question:
         * https://stackoverflow.com/questions/20493577/android-seekbar-thumb-position-in-pixel
         * one can find the SeekBar thumb location in pixels using:
         */
        val width = (sb.width - sb.paddingLeft - sb.paddingRight)
        val thumbPos = (sb.paddingLeft + (width * sb.progress / sb.max) +
            //take into consideration the margin added (in this case it is 10dp)
            convertDpToPixel(10f).roundToInt())
        tvProgress.text = message
        tvProgress.post {
//            val display: Display =
//                (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val deviceDisplay = Point()
//            display.getSize(deviceDisplay)

            //vArrow always follow seekBar thumb location
            vArrow.x = (thumbPos - sb.thumbOffset).toFloat()

            //unlike vArrow, tvProgress will not always follow seekBar thumb location
            when {
                thumbPos - tvProgress.width / 2 - sb.paddingLeft < 0 -> {
                    //part of the tvProgress is to the left of 0 bound
                    tvProgress.x = vArrow.x - 20
                }
                thumbPos + tvProgress.width / 2 + sb.paddingRight > deviceDisplay.x -> {
                    //part of the tvProgress is to the right of screen width bound
                    tvProgress.x = vArrow.x - tvProgress.width + 20 + vArrow.width
                }
                else -> {
                    //tvProgress is between 0 and screen width bounds
                    tvProgress.x = thumbPos - tvProgress.width / 2f
                }
            }
        }
    }

    private fun convertDpToPixel(dp: Float): Float {
        val resources: Resources = resources
        val metrics: DisplayMetrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater)

        binding.sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateMarker(binding.sb, binding.marker.rlMarker, progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.marker.rlMarker.visibility = View.VISIBLE
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.marker.rlMarker.visibility = View.GONE
            }
        })

        binding.btnGo.setOnClickListener {
            val navHostFragment: NavHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val mNavController = navHostFragment.navController

//        TimeUnit.SECONDS.sleep(1)
//        Thread.sleep(1000L)
//            Handler(Looper.getMainLooper()).postDelayed({
//                val findNavController = findNavController(R.id.fragmentContainerView)
//                findNavController.navigate(R.id.signUpFragment)
//            }, 1000L)
            mNavController.navigate(R.id.signUpFragment)
        }
        return binding.root
//        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = LoginFragment()
    }


}