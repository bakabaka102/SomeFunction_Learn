package com.app.func.view.animations_custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ArcAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 15f
        strokeCap = Paint.Cap.ROUND
    }

    private val arcRect = RectF()
    private var animatedAngle = 120f  // phần chớp/tăng giảm
    private var animator: ValueAnimator? = null
    private val start = 150f

    init {
        startAnim()
    }

    private fun startAnim() {
        animator = ValueAnimator.ofFloat(start, 210f).apply {
            duration = 1000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                animatedAngle = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = 40f
        arcRect.set(padding, padding, width.toFloat() - padding, height.toFloat() - padding)

        // 🎯 Vẽ cung 2/3 (từ 150 độ đến -240 độ, ngược kim đồng hồ nếu false)
        canvas.drawArc(arcRect, start, 240f, false, paint)

        // 🔄 Vẽ phần 1/3 đang được animate (giao động từ 90-210 độ)
        paint.color = Color.MAGENTA
        canvas.drawArc(arcRect, animatedAngle, 30f, false, paint)

        // Đặt lại màu chính
        paint.color = Color.CYAN
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
