package com.app.func

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.func.databinding.ActivityViewCustomBinding
import com.app.func.view.chart.stock.WaterTankTemperatureView

class ViewCustomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCustomBinding.inflate(layoutInflater)
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

        binding.viewTempProgress.setMinMaxProgress(40, 75)
        binding.viewTempProgress.setTemp(20)
        binding.viewTempProgress.setTemperatureTitle(20.toString())
        binding.viewTempProgress.settingTemp(50)

        binding.waterViewTempProgress.setMinMaxProgress(40, 75)
        binding.waterViewTempProgress.setTemp(20)
        binding.waterViewTempProgress.setTemperatureTitle(20.toString())
        binding.waterViewTempProgress.settingTemp(50)
        binding.waterViewTempProgress.updateState(WaterTankTemperatureView.State.WORKING)


    }
}