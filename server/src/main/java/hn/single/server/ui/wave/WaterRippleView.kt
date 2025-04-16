package hn.single.server.ui.wave

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import kotlin.math.min

class WaterRippleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val rippleCount = 4
    private val ripples = mutableListOf<Ripple>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        //style = Paint.Style.STROKE
        style = Paint.Style.FILL_AND_STROKE
    }

    private val maxRadius get() = (min(width, height) / 2).toFloat()
    private val centerX get() = width / 2f
    private val centerY get() = height / 2f

    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateRipples()
            invalidate()
            handler.postDelayed(this, 16) // ~60fps
        }
    }

    init {
        // Khởi tạo số lượng ripple cố định
        val interval = 1f / rippleCount
        for (i in 0 until rippleCount) {
            ripples.add(Ripple(startProgress = i * interval))
        }
        handler.post(updateRunnable)
    }

    private fun updateRipples() {
        for (ripple in ripples) {
            ripple.update()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (ripple in ripples) {
            paint.apply {
                color = ripple.color
                strokeWidth = ripple.strokeWidth
                canvas.drawCircle(centerX, centerY, ripple.radius, this)
            }
        }
    }

    private inner class Ripple(startProgress: Float) {
        var progress = startProgress // từ 0f đến 1f
        val speed = 0.008f // càng nhỏ càng chậm
        val colorBase = "#2196F3".toColorInt()
        val strokeWidth = 4f

        val radius: Float
            get() = progress * maxRadius

        val alpha: Int
            get() = ((1f - progress) * 255).toInt().coerceIn(0, 255)

        val color: Int
            get() = (colorBase and 0x00FFFFFF) or (alpha shl 24)

        fun update() {
            progress += speed
            if (progress >= 1f) progress = 0f
        }
    }
}

