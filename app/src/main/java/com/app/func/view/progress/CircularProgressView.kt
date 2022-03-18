package com.app.func.view.progress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.app.func.R

class CircularProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val ovalSpace = RectF()
    private val parentArcColor = Color.parseColor("#DFE6F3")
    private val fillArcColor = Color.parseColor("#0097A7")
    private val ovalSize = resources.getDimensionPixelSize(R.dimen._200dp)
    private val widthSize = resources.getDimensionPixelSize(R.dimen._40dp)
    private val parentArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = parentArcColor
        strokeWidth = 40.toFloat()
        strokeCap = Paint.Cap.ROUND
    }

    private val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = fillArcColor
        strokeWidth = 40.toFloat()
        strokeCap = Paint.Cap.ROUND
    }


    private fun createBackGroundCircle() {
        val horizontalCenter = width.div(2).toFloat()
        val verticalCenter = height.div(2).toFloat()
        val ovalSize = 200f
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun drawBackground(canvas: Canvas?) {
        canvas?.drawArc(ovalSpace, 135f, 270f, false, parentArcPaint)

    }

    private fun drawInnerArc(canvas: Canvas?, multiple: Int) {
        val percentageToFill = 10.8f
        canvas?.drawArc(ovalSpace, 135f, percentageToFill * multiple, false, fillArcPaint)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        createBackGroundCircle()
        drawBackground(canvas)
        drawInnerArc(canvas, 2)
//        for (i in 1..25) {
//            Thread.sleep(300)
//            drawInnerArc(canvas, i)
//            invalidate()
//        }


    }
}