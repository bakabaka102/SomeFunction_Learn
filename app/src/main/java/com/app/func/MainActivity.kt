package com.app.func

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.func.databinding.ActivityMainBinding
import com.app.func.view.all_demo.EmotionalFaceView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
            startActivity(Intent(this, Next3Activity::class.java))
        }

    }
}