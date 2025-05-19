package com.app.func

import androidx.core.view.isVisible
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityViewCustomBinding
import com.app.func.utils.Logger
import com.app.func.view.chart.stock.TemperatureView
import com.app.func.view.chart.stock.WaterTankTemperatureView

class ViewCustomActivity : BaseActivity<ActivityViewCustomBinding>() {

    override fun instanceViewBinding() = ActivityViewCustomBinding.inflate(layoutInflater)

    override fun initViews() {
        mBinding.customSeekBar.apply {
            setValueRange(1f, 18f)
            setValueCurrent(12f)
            onValueProgress = {
                mBinding.toolTipSeekBar.isVisible = true
                mBinding.toolTipSeekBar.x = it
            }
            onValueSelected = {
                mBinding.toolTipSeekBar.setContentString(it.toInt().toString())
            }
            actionShowHideTooltip = {
                mBinding.consToolTip.isVisible = it
            }
        }

        mBinding.viewTempProgress.apply {
            setMinMaxProgress(40, 75)
            setCurrentTemp(20)
            setTargetTemp(50)
            updateState(TemperatureView.State.STABLE)
        }

        mBinding.waterViewTempProgress.apply {
            setMinMaxProgress(40, 75)
            setTemp(40)
            settingTemp(50)
            updateState(WaterTankTemperatureView.State.WORKING)
        }
        //For demo or
        mBinding.arcPulGaugeView.setTargetValueAnimated(60)
        /*Or observer
        viewModel.heartRate.observe(viewLifecycleOwner) { bpm ->
            mBinding.arcPulGaugeView.targetValue(bpm)
        }
        or StateFlow
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.heartRateFlow.collect { bpm ->
                    arcGaugeView.setTargetValueAnimated(bpm)
                }
            }
        }*/
        mBinding.arcPulGaugeView.onValueChangedListener = {
            Logger.d("Current target: $it")
        }
    }
}