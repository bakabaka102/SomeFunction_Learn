package com.app.func

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.func.databinding.ActivityScrollBinding

class ScrollActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customSeekBar.setValueRange(1f, 18f)
        binding.customSeekBar.setValueCurrent(12f)

        binding.customSeekBar.onValueProgress = {
            binding.toolTipSeekBar.visibility = View.VISIBLE
            binding.toolTipSeekBar.x = it
        }

        binding.customSeekBar.onValueSelected = {
            binding.toolTipSeekBar.setContentString(it.toInt().toString())
        }

        binding.customSeekBar.actionShowHideTooltip = {
            if (it) {
                binding.consToolTip.visibility = View.VISIBLE
            } else {
                binding.consToolTip.visibility = View.GONE
            }
        }


    }
}