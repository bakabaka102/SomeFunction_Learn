package com.app.func.view.progress

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressViewCopy @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val ARC_FULL_ROTATION_DEGREE = 360
        const val PERCENTAGE_DIVIDER = 100.0
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    private var currentPercentage = 0
    private val ovalSpace = RectF()
    private val parentArcColor = Color.parseColor("#DFE6F3")
    private val fillArcColor = Color.parseColor("#D81B60")

    private val parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = parentArcColor
        strokeWidth = 40f
    }

    private val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = fillArcColor
        strokeWidth = 40f
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        setSpace()
        canvas.let {
            drawBackgroundArc(it)
            drawInnerArc(it)
        }
    }

    private fun setSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = 200
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun drawBackgroundArc(it: Canvas) {
        it.drawArc(ovalSpace, 0f, 360f, false, parentArcPaint)
    }

    private fun drawInnerArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(ovalSpace, 270f, percentageToFill, false, fillArcPaint)
    }

    private fun getCurrentPercentageToFill() =
        (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    fun animateProgress() {
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, 100f)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 5000
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }
        animator.start()
    }
}