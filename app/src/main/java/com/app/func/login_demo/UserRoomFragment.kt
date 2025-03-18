package com.app.func.login_demo

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentUserRoomBinding
import java.util.Locale


class UserRoomFragment : BaseFragment<FragmentUserRoomBinding>() {

    override fun getViewBinding() = FragmentUserRoomBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    private val mCountChangeListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            binding?.let {
                it.pulsator.count = progress + 1
                it.textCount.text = String.format(Locale.US, "%d", progress + 1)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }

    private val mDurationChangeListener: OnSeekBarChangeListener =
        object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding?.let {
                    it.pulsator.duration = progress * 100
                    it.textDuration.text = String.format(Locale.US, "%.1f", progress * 0.1f)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }

    override fun initActions() {
        binding?.let {
            it.seekCount.setOnSeekBarChangeListener(mCountChangeListener)
            it.seekCount.progress = it.pulsator.count - 1
            it.seekDuration.setOnSeekBarChangeListener(mDurationChangeListener)
            it.seekDuration.progress = it.pulsator.duration / 100
            it.pulsator.start()
        }
    }
}