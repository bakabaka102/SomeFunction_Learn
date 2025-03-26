package com.app.func.view.animation_view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import java.util.ArrayList

class WaveHelper(private val waveView: WaveWaterView?) {

    private var mAnimatorSet: AnimatorSet? = null

    fun start() {
        waveView?.isShowWave = true
        if (mAnimatorSet != null) {
            mAnimatorSet?.start()
        }
    }

    fun pause() {
        mAnimatorSet?.pause()
    }

    private fun initAnimation() {
        val animators: MutableList<Animator> = ArrayList()

        val waveShiftAnim = ObjectAnimator.ofFloat(
            waveView, "waveShiftRatio", 0f, 1f
        )
        waveShiftAnim.apply {
            repeatCount = ValueAnimator.INFINITE
            duration = DURATION_ANIMATION_WAVE
            interpolator = LinearInterpolator()
        }
        animators.add(waveShiftAnim)

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        val amplitudeAnim = ObjectAnimator.ofFloat(
            waveView, "amplitudeRatio", 0.02f, 0.02f
        )
        amplitudeAnim.apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            duration = DURATION_AMPLITUDE
            interpolator = LinearInterpolator()
        }
        animators.add(amplitudeAnim)
        mAnimatorSet = AnimatorSet().apply {
            playTogether(animators)
        }
    }

    init {
        initAnimation()
    }

    companion object {
        private const val DURATION_ANIMATION_WAVE = 2000L
        private const val DURATION_AMPLITUDE = 5000L
    }

}