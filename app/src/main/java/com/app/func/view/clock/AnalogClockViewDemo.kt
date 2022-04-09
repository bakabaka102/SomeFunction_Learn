package com.app.func.view.clock

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class AnalogClockViewDemo @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /*
    Preference:
    https://www.ssaurel.com/blog/learn-to-draw-an-analog-clock-on-android-with-the-canvas-2d-api/
    https://medium.com/@mayurjajoomj/custom-analog-clock-using-custom-view-android-429cc180f6a3
     */

    private var mRect = Rect()
    private var mHeight = 0
    private var mWidth = 0
    private var mRadius = 0
    private var mAngle = 0.0
    private var mCentreX = 0f
    private var mCentreY = 0f
    private var mPadding = 0
    private var mIsInit = false
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mNumbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private var mMinimum = 0
    private var mHour = 0
    private var mMinute = 0f
    private var mSecond = 0f
    private var mHourHandSize = 0
    private var mHandSize = 0
    private var mFontSize = 0
    private var mBlackPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
    }

    private fun init() {
        mFontSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            13f,
            resources.displayMetrics
        ).toInt()
        mHeight = height
        mWidth = width
        mPadding = 50
        mCentreX = mWidth / 2f
        mCentreY = mHeight / 2f
        mMinimum = min(mHeight, mWidth)
        mRadius = mMinimum / 2 - mPadding
        mAngle = (Math.PI / 30 - Math.PI / 2)
        mHourHandSize = mRadius - mRadius / 2
        mHandSize = mRadius - mRadius / 4
        mIsInit = true
    }

    private fun setPaintAttributes(colour: Int, stroke: Paint.Style, strokeWidth: Int) {
        mPaint.reset()
        mPaint.color = colour
        mPaint.style = stroke
        mPaint.strokeWidth = strokeWidth.toFloat()
        mPaint.isAntiAlias = true
    }

    private fun drawBackgroundCircle(canvas: Canvas?) {
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 5)
        canvas?.drawCircle(mCentreX, mCentreY, measuredWidth.toFloat(), mPaint)
    }

    private fun drawOuterCircle(canvas: Canvas?) {
        mBlackPaint.strokeWidth = 5f
        canvas?.drawCircle(
            measuredWidth / 2.toFloat(),
            measuredHeight / 2.toFloat(),
            (measuredWidth / 2 - 5).toFloat(),
            mBlackPaint
        )
    }

    private fun drawDotCenter(canvas: Canvas?) {
        setPaintAttributes(Color.BLACK, Paint.Style.FILL, 8)
        canvas?.drawCircle(mCentreX, mCentreY, 10f, mPaint)
    }

    private fun drawHand(canvas: Canvas?) {
        val calendar: Calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR_OF_DAY)
        //convert to 12hour format from 24 hour format
        mHour = if (mHour > 12) {
            mHour - 12
        } else {
            mHour
        }
        mMinute = calendar[Calendar.MINUTE].toFloat()
        mSecond = calendar[Calendar.SECOND].toFloat()
        drawHourHand(canvas, (mHour + mMinute / 60.0) * 5f)
        drawMinuteHand(canvas, mMinute)
        drawSecondsHand(canvas, mSecond)
    }

    private fun drawSecondsHand(canvas: Canvas?, mSecond: Float) {
        mPaint.reset()
        setPaintAttributes(Color.BLACK, Paint.Style.STROKE, 3)
        mAngle = Math.PI * mSecond / 30 - Math.PI / 2
        canvas?.drawLine(
            mCentreX,
            mCentreY,
            (mCentreX + cos(mAngle) * mRadius).toFloat(),
            (mCentreY + sin(mAngle) * mRadius).toFloat(),
            mPaint
        )

    }

    private fun drawMinuteHand(canvas: Canvas?, minute: Float) {
        mPaint.reset()
        setPaintAttributes(Color.MAGENTA, Paint.Style.STROKE, 4)
        mAngle = Math.PI * mMinute / 30 - Math.PI / 2
        canvas?.drawLine(
            mCentreX,
            mCentreY,
            (mCentreX + cos(mAngle) * 7 * mRadius / 8).toFloat(),
            (mCentreY + sin(mAngle) * 7 * mRadius / 8).toFloat(),
            mPaint
        )
    }

    private fun drawHourHand(canvas: Canvas?, hour: Double) {
        setPaintAttributes(Color.RED, Paint.Style.STROKE, 5)
        mAngle = Math.PI * hour / 30 - Math.PI / 2
        val stopX = (mCentreX + cos(mAngle) * 4 * mRadius / 5).toFloat()
        val stopY = (mCentreY + sin(mAngle) * 4 * mRadius / 5).toFloat()
        canvas?.drawLine(mCentreX, mCentreY, stopX, stopY, mPaint)
    }

    private fun drawNumerals(canvas: Canvas?) {
        mPaint.textSize = mFontSize.toFloat()
        for (number in mNumbers) {
            val num = number.toString()
            mPaint.getTextBounds(num, 0, num.length, mRect)
            val angle = Math.PI / 6 * (number - 3)
            val x = (mCentreX + cos(angle) * mRadius - mRect.width() / 2).toInt()
            val y = (mCentreY + sin(angle) * mRadius + mRect.height() / 2).toInt()
            canvas?.drawText(num, x.toFloat(), y.toFloat(), mPaint)
        }
    }

    private fun drawScale(canvas: Canvas?) {
        var scaleLength: Float
        canvas?.save()
        //0.. 59 for [0,59]
        for (i in 0..59) {
            if (i % 5 == 0) {
                mBlackPaint.strokeWidth = 5f
                scaleLength = 20f
            } else {
                mBlackPaint.strokeWidth = 3f
                scaleLength = 10f
            }
            canvas?.drawLine(
                measuredWidth / 2.toFloat(),
                5f,
                measuredWidth / 2.toFloat(),
                5 + scaleLength,
                mBlackPaint
            )
            canvas?.rotate(
                360f / 60,
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat()
            )
        }
        //Restore the original state
        canvas?.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!mIsInit) {
            init()
        }
        drawScale(canvas)
        //drawBackgroundCircle(canvas)
        drawOuterCircle(canvas)
        drawHand(canvas)
        drawDotCenter(canvas)
        drawNumerals(canvas)
        postInvalidateDelayed(500)
        invalidate()
    }
}