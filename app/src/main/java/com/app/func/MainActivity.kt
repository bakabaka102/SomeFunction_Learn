package com.app.func

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.func.databinding.ActivityMainBinding
import com.app.func.view.all_demo.EmotionalFaceView
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var binding: ActivityMainBinding

    //PinchZoomText
    private var mRatio = 1.0f
    private val move = 200f
    private var ratio = 1.0f
    private var bastDst = 0
    private var baseratio = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.happyButton.setOnClickListener {
            binding.emotionalFaceView.happinessState = EmotionalFaceView.HAPPY
        }

        binding.sadButton.setOnClickListener {
            binding.emotionalFaceView.happinessState = EmotionalFaceView.SAD
        }

        binding.btnNext1.setOnClickListener {
            startActivity(Intent(this, MultiThreadActivity::class.java))
        }
        binding.btnNext2.setOnClickListener {
            startActivity(Intent(this, ViewCustomActivity::class.java))
        }
        binding.btnNext3.setOnClickListener {
            startActivity(Intent(this, ViewAnimationsActivity::class.java))
        }
        binding.btnAnimation.setOnClickListener {
            startActivity(Intent(this, ViewAnimationsActivity2::class.java))
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount == 2) {
            val action = event.action
            val mainAction = action and MotionEvent.ACTION_MASK
            if (mainAction == MotionEvent.ACTION_POINTER_DOWN) {
                bastDst = getDistance(event)
                baseratio = ratio
            } else {
                // if ACTION_POINTER_UP then after finding the distance
                // we will increase the text size by 15
                val scale = (getDistance(event) - bastDst) / move
                val factor = 2.0.pow(scale.toDouble()).toFloat()
                ratio = 1024.0f.coerceAtMost(0.1f.coerceAtLeast(baseratio * factor))
            }
        }
        return true
    }

    // get distance between the touch event
    private fun getDistance(event: MotionEvent): Int {
        val dx = (event.getX(0) - event.getX(1)).toInt()
        val dy = (event.getY(0) - event.getY(1)).toInt()
        return sqrt((dx * dx + dy * dy).toDouble()).toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return false
    }
}