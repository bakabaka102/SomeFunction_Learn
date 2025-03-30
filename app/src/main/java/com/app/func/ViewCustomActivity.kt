package com.app.func

import android.view.View
import androidx.core.view.isVisible
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityViewCustomBinding
import com.app.func.utils.Logger
import com.app.func.view.chart.stock.WaterTankTemperatureView
import com.app.func.view.seekbarcustom.crollerTest.Croller
import com.app.func.view.seekbarcustom.crollerTest.OnCrollerChangeListener
import com.app.func.view.seekbarcustom.crollerTest.OnProgressChangedListener

class ViewCustomActivity : BaseActivity<ActivityViewCustomBinding>() {

    override fun instanceViewBinding() = ActivityViewCustomBinding.inflate(layoutInflater)

    override fun initViews() {
        mBinding.customSeekBar.apply {
            setValueRange(1f, 18f)
            setValueCurrent(12f)
            onValueProgress = {
                mBinding.toolTipSeekBar.visibility = View.VISIBLE
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
            setTemp(20)
            setTemperatureTitle(20.toString())
            settingTemp(50)
        }

        mBinding.waterViewTempProgress.apply {
            setMinMaxProgress(40, 75)
            setTemp(20)
            setTemperatureTitle(20.toString())
            settingTemp(50)
            updateState(WaterTankTemperatureView.State.WORKING)
        }
        initScrollSeekbar()
    }

    override fun initActions() {

    }

    private fun initScrollSeekbar() {
        /*mBinding.crollerSeekbar.apply {
            setIndicatorWidth(10f)
            setBackCircleColor(Color.parseColor("#EDEDED"))
            setMainCircleColor(Color.WHITE)
            setMax(50)
            setStartOffset(45)
            setIsContinuous(false)
            setLabelColor(Color.BLACK)
            setProgressPrimaryColor(Color.parseColor("#0B3C49"))
            setIndicatorColor(Color.parseColor("#0B3C49"))
            setProgressSecondaryColor(Color.parseColor("#EEEEEE"))
        }*/

        mBinding.crollerSeekbar.setOnCrollerChangeListener(object : OnCrollerChangeListener {
            override fun onProgressChanged(croller: Croller?, progress: Int) {
                Logger.d("setOnCrollerChangeListener ----  $progress")
            }

            override fun onStartTrackingTouch(croller: Croller?) {

            }

            override fun onStopTrackingTouch(croller: Croller?) {

            }
        })

        mBinding.crollerSeekbar.setOnProgressChangedListener(object : OnProgressChangedListener {
            override fun onProgressChanged(progress: Int) {
                // use the progress
                Logger.d("setOnProgressChangedListener ------ $progress")
            }
        })
    }
}