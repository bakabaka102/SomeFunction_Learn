package com.app.func

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.func.databinding.ActivityMainBinding
import com.app.func.view.TopAlignSuperscriptSpan
import com.app.func.view.all_demo.EmotionalFaceView


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
            startActivity(Intent(this, SecondActivity::class.java))
        }
        binding.btnNext2.setOnClickListener {
            startActivity(Intent(this, ScrollActivity::class.java))
        }
        binding.btnNext3.setOnClickListener {
            startActivity(Intent(this, MainFragmentsActivity::class.java))
        }

        val spannableString = SpannableString("@RM123.456")
        spannableString.setSpan(
            TopAlignSuperscriptSpan(0.35f), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.textDemo.text = spannableString


        binding.textPinchZoom.textSize = mRatio + 13

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
                val factor = Math.pow(2.0, scale.toDouble()).toFloat()
                ratio = Math.min(1024.0f, Math.max(0.1f, baseratio * factor))
                binding.textPinchZoom.textSize = ratio + 15
            }
        }
        return true
    }

    // get distance between the touch event
    private fun getDistance(event: MotionEvent): Int {
        val dx = (event.getX(0) - event.getX(1)).toInt()
        val dy = (event.getY(0) - event.getY(1)).toInt()
        return Math.sqrt((dx * dx + dy * dy).toDouble()).toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return false
    }
}